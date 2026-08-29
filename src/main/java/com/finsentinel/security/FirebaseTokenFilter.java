package com.finsentinel.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            token = request.getParameter("token"); // For SSE
        }

        if (token != null && !token.isEmpty()) {
            if ("mock-token".equals(token)) {
                // Mock Auth Mode bypass for out-of-the-box demo
                request.setAttribute("tenantId", "demo-tenant");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        "demo-user", null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                try {
                    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                    String uid = decodedToken.getUid();
                    
                    // Get tenantId from custom claims, or use a default one based on uid
                    String tenantId = (String) decodedToken.getClaims().get("tenantId");
                    if (tenantId == null) {
                        tenantId = "tenant-" + uid; // fallback logic
                    }
                    
                    // Expose tenantId via request attribute for controllers to use
                    request.setAttribute("tenantId", tenantId);
                    
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            uid, null, Collections.emptyList()); // Add roles here if needed
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (FirebaseAuthException | IllegalArgumentException e) {
                    System.err.println("Firebase Auth Error: " + e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
