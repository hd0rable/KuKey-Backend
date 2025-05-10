package com.example.Kukey_Backend.global.handler;

import com.example.Kukey_Backend.domain.notification.domain.dto.response.DiscordMessage;
import com.example.Kukey_Backend.global.exception.GlobalException;
import com.example.Kukey_Backend.global.util.DiscordFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.Kukey_Backend.domain.notification.domain.dto.response.DiscordMessage.of;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.INVALID_DISCORD_MESSAGE;

@RequiredArgsConstructor
@Component
public class DiscordMessageProvider {

    private final DiscordFeignClient discordFeignClient;

    public void sendMessage(String message) {
        DiscordMessage discordMessage = of(message);
        sendMessageToDiscord(discordMessage);
    }

    private void sendMessageToDiscord(DiscordMessage discordMessage) {
        try {
            discordFeignClient.sendMessage(discordMessage);
        } catch (FeignException e) {
            throw new GlobalException(INVALID_DISCORD_MESSAGE);
        }
    }
}