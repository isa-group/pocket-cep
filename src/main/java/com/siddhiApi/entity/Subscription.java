package com.siddhiApi.entity;

import java.util.UUID;

public class Subscription {
    private String identifier, webhook, method;

    public Subscription(String webhook, String method) {
        this.identifier = UUID.randomUUID().toString();
        this.webhook = webhook;
        this.method = method;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    @Override
    public String toString() {
        return "Subscription: " +
                "identifier= '" + identifier + '\'' +
                ", webhook= '" + webhook + '\'' +
                ", method= '" + method + '\'';
    }
}
