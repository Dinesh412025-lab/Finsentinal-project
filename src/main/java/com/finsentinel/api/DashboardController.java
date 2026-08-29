package com.finsentinel.api;

import com.finsentinel.agents.*;
import com.finsentinel.cnp.MessageBroker;
import com.finsentinel.cnp.CnpMessage;
import com.finsentinel.cnp.CnpPerformative;
import com.finsentinel.data.TransactionGenerator;
import com.finsentinel.data.TransactionRecord;
import com.finsentinel.ml.TribuoForecaster;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agents")
public class DashboardController {

    private final ReconciliationAgent reconAgent;
    private final AnomalyAgent anomalyAgent;
    private final ForecastingAgent forecastAgent;
    private final ComplianceAgent complianceAgent;
    private final OrchestratorAgent orchestratorAgent;
    private final TransactionGenerator generator;
    private final TribuoForecaster forecaster;
    private final MessageBroker broker;

    public DashboardController(ReconciliationAgent reconAgent, AnomalyAgent anomalyAgent, ForecastingAgent forecastAgent,
                               ComplianceAgent complianceAgent, OrchestratorAgent orchestratorAgent,
                               TransactionGenerator generator, TribuoForecaster forecaster, MessageBroker broker) {
        this.reconAgent = reconAgent;
        this.anomalyAgent = anomalyAgent;
        this.forecastAgent = forecastAgent;
        this.complianceAgent = complianceAgent;
        this.orchestratorAgent = orchestratorAgent;
        this.generator = generator;
        this.forecaster = forecaster;
        this.broker = broker;
    }

    @GetMapping("/status")
    public Map<String, Map<String, Object>> getStatus() {
        Map<String, Map<String, Object>> status = new HashMap<>();
        status.put("recon", Map.of("status", reconAgent.getStatus(), "log", reconAgent.getLogs()));
        status.put("anomaly", Map.of("status", anomalyAgent.getStatus(), "log", anomalyAgent.getLogs()));
        status.put("forecast", Map.of("status", forecastAgent.getStatus(), "log", forecastAgent.getLogs()));
        status.put("compliance", Map.of("status", complianceAgent.getStatus(), "log", complianceAgent.getLogs()));
        status.put("orchestrator", Map.of("status", orchestratorAgent.getStatus(), "log", orchestratorAgent.getLogs()));
        return status;
    }

    @GetMapping(value = "/feed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TransactionRecord> getLiveFeed() {
        return Flux.interval(Duration.ofSeconds(2)).map(sequence -> {
            TransactionRecord txn = generator.generateNext();
            
            // Trigger CNP negotiation for reconciliation
            broker.send(CnpMessage.builder()
                    .senderId("orchestrator")
                    .receiverId("recon")
                    .performative(CnpPerformative.CFP)
                    .content("RECONCILE:" + txn.getId())
                    .build());
            
            if (txn.isAnomaly()) {
                txn.setStatus("Mismatched");
                // Trigger CNP negotiation for anomaly detection
                broker.send(CnpMessage.builder()
                        .senderId("recon")
                        .receiverId("anomaly")
                        .performative(CnpPerformative.CFP)
                        .content("CHECK_ANOMALY:" + txn.getId())
                        .build());
            } else {
                txn.setStatus("Matched");
            }
            
            return txn;
        });
    }

    @GetMapping("/forecast")
    public List<TribuoForecaster.ForecastData> getForecast(@RequestParam(defaultValue = "0") int delayDays, @RequestParam(defaultValue = "1.0") double volumeMultiplier) {
        return forecaster.generateForecast(delayDays, volumeMultiplier);
    }
    
    @GetMapping("/chat")
    public Map<String, String> chatCopilot(@RequestParam String question) {
        Map<String, String> res = new HashMap<>();
        res.put("answer", orchestratorAgent.askQuestion(question));
        return res;
    }
    
    @GetMapping("/calendar")
    public List<String> getComplianceCalendar() {
        return complianceAgent.getCalendar();
    }
    
    @GetMapping("/brief")
    public Map<String, String> getBrief() {
        Map<String, String> res = new HashMap<>();
        res.put("summary", "System is stable. Processed transactions for the day. Found 1 anomaly requiring attention.");
        res.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return res;
    }

    @GetMapping("/audit-log")
    public List<Map<String, String>> getAuditLog() {
        List<Map<String, String>> fullLog = new ArrayList<>();
        int id = 1;
        
        for (String log : orchestratorAgent.getLogs()) { fullLog.add(buildLogEntry(id++, "orchestrator", log)); }
        for (String log : reconAgent.getLogs()) { fullLog.add(buildLogEntry(id++, "recon", log)); }
        for (String log : anomalyAgent.getLogs()) { fullLog.add(buildLogEntry(id++, "anomaly", log)); }
        for (String log : forecastAgent.getLogs()) { fullLog.add(buildLogEntry(id++, "forecast", log)); }
        for (String log : complianceAgent.getLogs()) { fullLog.add(buildLogEntry(id++, "compliance", log)); }
        
        return fullLog;
    }
    
    private Map<String, String> buildLogEntry(int id, String agent, String action) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("agent", agent);
        map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        map.put("action", action);
        
        com.finsentinel.security.AuditChain.Block block = com.finsentinel.security.AuditChain.addLog(map);
        map.put("hash", block.hash);
        
        return map;
    }
}
