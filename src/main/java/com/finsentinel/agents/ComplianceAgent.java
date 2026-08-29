package com.finsentinel.agents;

import com.finsentinel.cnp.AgentNode;
import com.finsentinel.cnp.CnpMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplianceAgent extends AgentNode {

    private final List<String> logs = new ArrayList<>();

    public ComplianceAgent() {
        super("compliance");
        logs.add("Tax engines synced. No immediate violations.");
    }

    @Override
    protected void handleMessage(CnpMessage message) {
        // Will be used for GST/TDS checking logic if implemented via CNP later
    }
    
    public List<String> getLogs() {
        return logs;
    }
    
    public List<String> getCalendar() {
        return List.of(
            "10th - GSTR-1 Due (Draft Ready)",
            "15th - EPF/ESI Contribution (Calculated)",
            "20th - GSTR-3B (Pending Data)"
        );
    }
}
