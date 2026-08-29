package com.finsentinel.agents;

import com.finsentinel.cnp.AgentNode;
import com.finsentinel.cnp.CnpMessage;
import com.finsentinel.services.CopilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrchestratorAgent extends AgentNode {

    private final List<String> logs = new ArrayList<>();
    
    @Autowired
    private CopilotService copilotService;

    public OrchestratorAgent() {
        super("orchestrator");
        logs.add("Initialized FinSentinel subsystems.");
    }

    @Override
    protected void handleMessage(CnpMessage message) {
        // Collects results from other agents to build the brief.
        logs.add("Received data from " + message.getSenderId());
    }
    
    public String askQuestion(String question) {
        String context = String.join(" | ", logs);
        return copilotService.askCopilot(question, context);
    }
    
    public List<String> getLogs() {
        return logs;
    }
}
