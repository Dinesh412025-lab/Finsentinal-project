package com.finsentinel.cnp;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

public abstract class AgentNode {

    @Getter
    protected String agentId;
    
    @Getter
    protected String status = "Idle"; // Idle, Bidding, Executing, Done

    @Autowired
    protected MessageBroker broker;

    public AgentNode(String agentId) {
        this.agentId = agentId;
    }

    @PostConstruct
    public void init() {
        broker.register(this);
    }

    @Async
    public void receiveMessage(CnpMessage message) {
        handleMessage(message);
    }

    protected abstract void handleMessage(CnpMessage message);

    protected void send(CnpMessage message) {
        message.setSenderId(this.agentId);
        broker.send(message);
    }
    
    protected void updateStatus(String newStatus) {
        this.status = newStatus;
    }
}
