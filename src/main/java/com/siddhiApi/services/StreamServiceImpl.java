package com.siddhiApi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhiApi.dao.*;
import com.siddhiApi.entity.Pattern;
import com.siddhiApi.entity.Stream;
import com.siddhiApi.entity.Subscription;
import com.siddhiApi.exceptions.DuplicatedEntity;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.exceptions.StreamOnUseException;
import com.siddhiApi.multithreading.SendEventSubscriptions;
import com.siddhiApi.multithreading.SendEventTask;
import com.siddhiApi.util.Parsers;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class StreamServiceImpl implements StreamService{

    private static final ExecutorService executorForSendEvent = Executors.newSingleThreadExecutor(); //Anterior 5
    private static final ExecutorService executorForSubscriptions = Executors.newFixedThreadPool(3); //Anterior 5

    Logger logger = LoggerFactory.getLogger(StreamServiceImpl.class);

    @Autowired
    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl();

    @Autowired
    private final StreamDAO streamDAO = new StreamDAOImpl();

    @Autowired
    private final PatternDAO patternDAO = new PatternDAOImpl();

    private void validateStream(Stream stream) throws NullPointerException{
        try{
            stream.getStreamID();
            stream.getJsonSchema();
        }catch(NullPointerException e){
            throw new NullPointerException("There is a missing parameter in stream.");
        }
    }

    private void validateSubscription(Subscription subscription) throws NullPointerException{
        try{
            subscription.getWebhook();
            subscription.getMethod();
        }catch(NullPointerException e){
            throw new NullPointerException("There is a missing parameter in subscription.");
        }
    }


    @Override
    public void createStream(Stream stream) throws DuplicatedEntity {
        validateStream(stream);
        streamDAO.createStream(stream);
    }

    @Override
    public Stream[] getStreams() {
        return streamDAO.getStreams();
    }

    @Override
    public Stream getStream(String stream) throws NotFoundException {
        return streamDAO.getStream(stream);
    }

    private boolean streamBeingUsed(Pattern pattern, String stream){
        return pattern.getOutputStreamName().equals(stream) || Arrays.asList(pattern.getInputStreamNames()).contains(stream);
    }

    @Override
    public void removeStream(String stream) throws NotFoundException, StreamOnUseException {
        Pattern[] patterns = patternDAO.getPatterns();
        Pattern[] patternsUsingStream = Arrays.stream(patterns)
                .filter(pattern -> streamBeingUsed(pattern, stream))
                .toArray(Pattern[]::new);

        if (patternsUsingStream.length > 0){
            Optional<String> listOfPatterns = Arrays.stream(patternsUsingStream).map(Pattern::getPatternName).reduce((pattern1, pattern2) -> pattern1 + "\n" + pattern2);
            throw new StreamOnUseException("The stream is being used, and so it cannot be removed. The patterns using this stream are: " + listOfPatterns.get());
        }
        subscriptionDAO.removeAllSubscriptionsOfAStream(stream);
        streamDAO.removeStream(stream);

    }

    @Override
    public void sendEvent(String stream, Object event) throws ValidationException, JsonProcessingException, NotFoundException {
        JSONObject streamSchema = streamDAO.getStream(stream).getJsonSchema();

        ObjectMapper mapper = new ObjectMapper();
        JSONObject eventSchema = new JSONObject(mapper.writeValueAsString(event));

        Schema schema = SchemaLoader.load(streamSchema);
        schema.validate(eventSchema);

        Object[] eventParsed = Parsers.JsonObjectToObjectArray(eventSchema);
        for (Object object: eventParsed){
            logger.info("Property on event already parsed: " + object);
        }
        executorForSubscriptions.execute(new SendEventSubscriptions(stream, eventSchema));
        executorForSendEvent.execute(new SendEventTask(stream, eventParsed));
    }

    @Override
    public Subscription subscribe(String streamID, Subscription subscription) throws NotFoundException {
        validateSubscription(subscription);
        Stream stream = this.getStream(streamID);
        return subscriptionDAO.subscribe(streamID, subscription);
    }

    @Override
    public Subscription[] getSubscriptions(String streamID) throws NotFoundException {
        Stream stream = this.getStream(streamID);
        return subscriptionDAO.getSubscriptions(streamID);
    }

    @Override
    public Subscription getSubscription(String streamID, String id) throws NotFoundException {
        Stream stream = this.getStream(streamID);
        return subscriptionDAO.getSubscription(streamID, id);
    }

    @Override
    public void unsubscribe(String streamID, String subscriptionID) throws NotFoundException {
        Stream stream = this.getStream(streamID);
        subscriptionDAO.unsubscribe(streamID, subscriptionID);
    }

    @Override
    public void updateSubscription(String streamID, String subscriptionID, Subscription subscription) throws NotFoundException {
        validateSubscription(subscription);
        subscriptionDAO.updateSubscription(streamID, subscriptionID, subscription);
    }

    /*@Override
    public String getStreamSubscriptionsToString(String streamID) {
        return subscriptionDAO.getSubscriptionsToString(streamID);
    }*/
}
