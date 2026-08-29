package com.finsentinel.cnp;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageBroker {

    private final ApplicationEventPublisher publisher;
    private final Map<String, AgentNode> registry = new ConcurrentHashMap<>();

    public MessageBroker(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void register(AgentNode agent) {
        registry.put(agent.getAgentId(), agent);
    }

    public void send(CnpMessage message) {
        if (message.getReceiverId() != null && registry.containsKey(message.getReceiverId())) {
            registry.get(message.getReceiverId()).receiveMessage(message);
        } else {
            // Broadcast
            registry.values().forEach(agent -> {
                if (!agent.getAgentId().equals(message.getSenderId())) {
                    agent.receiveMessage(message);
                }
            });
        }
    }
}
