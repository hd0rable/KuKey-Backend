package com.example.Kukey_Backend.domain.notification.domain.dto.response;

public record DiscordMessage(
        String content
) {
    public static DiscordMessage of(String message) {
        return new DiscordMessage(message);
    }
}

