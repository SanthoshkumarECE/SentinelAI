package com.sentinelai.sentinelai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiTestController {

    private final ChatClient chatClient;

    public AiTestController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/api/v1/ai/test")
    public String testAi(
            @RequestParam(defaultValue = "Database connection timeout") String message) {

        return chatClient.prompt()
                .system("""
                        You are SentinelAI, an application incident analysis assistant.
                        Treat your explanations as hypotheses, not confirmed root causes.
                        Answer concisely.
                        """)
                .user("Analyze this application error: " + message
                        + "\nGive two possible causes and three investigation steps.")
                .call()
                .content();
    }
}
