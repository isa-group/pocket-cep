package com.siddhiApi.multithreading;

import com.siddhiApi.dao.SubscriptionDAO;
import com.siddhiApi.dao.SubscriptionDAOImpl;
import com.siddhiApi.entity.Subscription;
import com.siddhiApi.webhook.WebhookMediator;
import org.json.JSONObject;

import java.util.Arrays;

public class SendEventSubscriptions implements Runnable{
    private final String streamName;
    private final JSONObject event;
    static SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl();

    public SendEventSubscriptions(String streamName, JSONObject event) {
        this.streamName = streamName;
        this.event = event;
    }

    @Override
    public void run(){
        Subscription[] subscriptions = subscriptionDAO.getSubscriptions(streamName);
        if (subscriptions != null){
            WebhookMediator.webhookFromSubscription(Arrays.asList(subscriptions), event);
        }
    }
}
