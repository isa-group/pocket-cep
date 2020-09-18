package com.siddhiApi.multithreading;

import com.siddhiApi.siddhiApplicationManager.SiddhiApplicationManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventExecutor{
    private static EventExecutor eventExecutor;
    //private final ExecutorService executorProducer;
    private final ExecutorService executorConsumer;

    private EventExecutor(){
        //this.executorProducer = Executors.newFixedThreadPool(4); //Anterior 3
        this.executorConsumer = Executors.newFixedThreadPool(4); //Anterior 5
    }

    public void executeProducer(String streamName, Object[] event){
        //executorProducer.execute(new Producer(streamName, event));
    }

    public void executeConsumer(){
        executorConsumer.execute(new Consumer());
    }

    public static EventExecutor getEventExecutor(){
        if (eventExecutor == null){
            eventExecutor = new EventExecutor();
        }
        return eventExecutor;
    }



    /*public static class Producer implements Runnable{
        String streamName;
        Object[] event;

        public Producer(String streamName, Object[] event) {
            this.streamName = streamName;
            this.event = event;
        }

        @Override
        public void run() {
            SendEventProducerConsumer.addPetition(new SendEventProducerConsumer.SendEventPetition(streamName, event));
        }
    }*/

    public static class Consumer implements Runnable{
        SendEventProducerConsumer.SendEventPetition sendEventPetition;

        @Override
        public void run() {
            while (true){
                sendEventPetition = SendEventProducerConsumer.handlePetition();
                SiddhiApplicationManager.sendEvent(sendEventPetition.getStreamName(), sendEventPetition.getEvent());
            }
        }
    }
}


