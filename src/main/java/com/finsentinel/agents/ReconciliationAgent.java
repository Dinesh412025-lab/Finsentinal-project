package com.finsentinel.agents;

import com.finsentinel.cnp.AgentNode;
import com.finsentinel.cnp.CnpMessage;
import com.finsentinel.cnp.CnpPerformative;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReconciliationAgent extends AgentNode {

    private final List<String> logs = new ArrayList<>();

    public ReconciliationAgent() {
        super("recon");
        logs.add("Initiated daily scan.");
    }

    @Override
    protected void handleMessage(CnpMessage message) {
        if (message.getPerformative() == CnpPerformative.CFP && message.getContent().startsWith("RECONCILE:")) {
            updateStatus("Scanning");
            String txnId = message.getContent().split(":")[1];
            logs.add("Matching " + txnId);
            
            // In a full CNP, we would Propose. For brevity, we'll auto-propose.
            send(CnpMessage.builder()
                    .senderId(agentId)
                    .receiverId(message.getSenderId())
                    .performative(CnpPerformative.PROPOSE)
                    .content("Can match " + txnId)
                    .replyWith(message.getId())
                    .build());
        }
    }
    
    public List<String> getLogs() {
        return logs;
    }
}
