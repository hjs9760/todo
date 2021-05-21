package com.js.ms.todo.global.config.exception;


import com.js.ms.todo.global.config.slack.SlackChannel;
import com.js.ms.todo.global.config.slack.SlackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionEventListener {

    private final SlackMessage slackMessage;

    @EventListener
    public void handle(Exception e) {
        slackMessage.sendSlackMessage(SlackChannel.CHANNEL_EXCEPTION, e.getMessage());
    }
}
