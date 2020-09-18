package com.siddhiApi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.siddhiApi.entity.Stream;
import com.siddhiApi.entity.Subscription;
import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.exceptions.StreamOnUseException;
import org.everit.json.schema.ValidationException;


public interface StreamService {
    void createStream(Stream stream) throws DuplicatedEntity;

    Stream[] getStreams();

    Stream getStream(String stream) throws NotFoundException;

    void removeStream(String stream) throws NotFoundException, StreamOnUseException;

    void sendEvent(String stream, Object event) throws ValidationException, JsonProcessingException, NotFoundException;

    Subscription subscribe(String streamID, Subscription subscription) throws NotFoundException;

    Subscription[] getSubscriptions(String streamID) throws NotFoundException;

    Subscription getSubscription(String streamID, String id) throws NotFoundException;

    void unsubscribe(String streamID, String subscriptionID) throws NotFoundException;

    void updateSubscription(String streamID, String subscriptionID, Subscription subscription) throws NotFoundException;



    //String getStreamSubscriptionsToString(String streamID);
}
