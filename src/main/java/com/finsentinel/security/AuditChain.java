package com.finsentinel.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuditChain {

    public static class Block {
        public int index;
        public String previousHash;
        public String hash;
        public String data;
        
        public Block(int index, String previousHash, String data) {
            this.index = index;
            this.previousHash = previousHash;
            this.data = data;
            this.hash = calculateHash();
        }
        
        public String calculateHash() {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String input = index + previousHash + data;
                byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final List<Block> chain = new ArrayList<>();
    
    static {
        // Genesis block
        chain.add(new Block(0, "0", "Genesis Block"));
    }

    public static synchronized Block addLog(Map<String, String> logEntry) {
        Block previous = chain.get(chain.size() - 1);
        String data = logEntry.toString();
        Block next = new Block(chain.size(), previous.hash, data);
        chain.add(next);
        return next;
    }
}
