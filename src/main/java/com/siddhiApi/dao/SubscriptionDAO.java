package com.siddhiApi.dao;

import com.siddhiApi.entity.Subscription;
import com.siddhiApi.exceptions.NotFoundException;
import org.json.JSONObject;

public interface SubscriptionDAO {
    Subscription subscribe(String streamID, Subscription subscription);

    Subscription[] getSubscriptions(String streamID);

    Subscription getSubscription(String streamID, String subscriptionID) throws NotFoundException;

    void unsubscribe(String streamID, String subscriptionID) throws NotFoundException;

    void removeAllSubscriptionsOfAStream(String streamID);

    void updateSubscription(String streamID, String subscriptionID, Subscription subscription) throws NotFoundException;

    //String getSubscriptionsToString(String streamID);
}
