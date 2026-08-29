package com.finsentinel.services;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopilotService {

    private OpenAiService openAiService;
    private final String defaultApiKey = System.getenv("OPENAI_API_KEY");

    public CopilotService() {
        if (defaultApiKey != null && !defaultApiKey.isEmpty()) {
            this.openAiService = new OpenAiService(defaultApiKey);
        } else {
            System.err.println("WARNING: OPENAI_API_KEY not set. Copilot will return simulated answers.");
        }
    }

    public String askCopilot(String question, String contextData) {
        if (openAiService == null) {
            return "Simulated Copilot response to: '" + question + "'. (Please set OPENAI_API_KEY for real LLM responses).";
        }
        
        try {
            ChatMessage systemMessage = new ChatMessage("system", "You are the FinSentinel Copilot, an AI Finance Controller. Ground your answers using the provided context: " + contextData);
            ChatMessage userMessage = new ChatMessage("user", question);
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(systemMessage, userMessage))
                    .maxTokens(150)
                    .build();
            
            return openAiService.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling OpenAI API: " + e.getMessage();
        }
    }
}
