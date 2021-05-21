package com.js.ms.todo.global.config.slack;

import lombok.Getter;

@Getter
public enum SlackChannel {

    CHANNEL_EXCEPTION("https://hooks.slack.com/services/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"),
    CHANNEL_MEMBER("https://hooks.slack.com/services/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");

    private String url;

    SlackChannel(String url) {
        this.url = url;
    }
}
