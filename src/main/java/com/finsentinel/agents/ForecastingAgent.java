package com.finsentinel.agents;

import com.finsentinel.cnp.AgentNode;
import com.finsentinel.cnp.CnpMessage;
import com.finsentinel.ml.TribuoForecaster;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastingAgent extends AgentNode {

    private final TribuoForecaster forecaster;
    private final List<String> logs = new ArrayList<>();

    public ForecastingAgent(TribuoForecaster forecaster) {
        super("forecast");
        this.forecaster = forecaster;
        logs.add("Tribuo models active. 90-day projection loaded.");
    }

    @Override
    protected void handleMessage(CnpMessage message) {
        // Receives market updates or simulation requests
    }
    
    public List<String> getLogs() {
        return logs;
    }
}
