package com.finsentinel.cnp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CnpMessage {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String senderId;
    private String receiverId;
    private CnpPerformative performative;
    private String content; // JSON or string payload
    private String replyWith; // ID of the message this responds to
}
