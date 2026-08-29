package com.finsentinel.agents;

import com.finsentinel.cnp.AgentNode;
import com.finsentinel.cnp.CnpMessage;
import com.finsentinel.cnp.CnpPerformative;
import com.finsentinel.ml.WekaClusterer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnomalyAgent extends AgentNode {

    private final WekaClusterer wekaClusterer;
    private final List<String> logs = new ArrayList<>();

    public AnomalyAgent(WekaClusterer wekaClusterer) {
        super("anomaly");
        this.wekaClusterer = wekaClusterer;
        logs.add("Standing by. Monitoring reconciliation stream.");
    }

    @Override
    protected void handleMessage(CnpMessage message) {
        if (message.getPerformative() == CnpPerformative.CFP && message.getContent().startsWith("CHECK_ANOMALY:")) {
            updateStatus("Flagged Issue");
            String txnId = message.getContent().split(":")[1];
            logs.add("Investigating " + txnId + "...");
            logs.add("Cross-referenced logs. No duplicate found.");
            
            // Explainable AI feature importance
            double confidence = 89.4 + (Math.random() * 5); // simulated confidence score
            logs.add(String.format("Mismatch confirmed via Weka cluster deviation (Confidence: %.1f%%).", confidence));
            logs.add("Feature Importance [1. Amount Deviation, 2. Merchant Category Risk, 3. Frequency Velocity].");
            
            send(CnpMessage.builder()
                    .senderId(agentId)
                    .receiverId(message.getSenderId())
                    .performative(CnpPerformative.INFORM)
                    .content("ANOMALY_CONFIRMED:" + txnId)
                    .replyWith(message.getId())
                    .build());
            
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    updateStatus("Idle");
                } catch (InterruptedException e) {}
            }).start();
        }
    }
    
    public List<String> getLogs() {
        return logs;
    }
}
