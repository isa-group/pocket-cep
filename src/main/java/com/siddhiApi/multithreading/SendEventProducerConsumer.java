package com.siddhiApi.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SendEventProducerConsumer {
    static List<SendEventPetition> petitions = new ArrayList<>(50);
    static int lastElement = -1;
    static ReentrantLock lock = new ReentrantLock();
    static Condition producer = lock.newCondition();
    static Condition consumer = lock.newCondition();
    
    public static void addPetition(SendEventPetition sendEventPetition){
        lock.lock();
        try {
            while(lastElement > 49){
                producer.await();
            }
            petitions.add(sendEventPetition);
            ++lastElement;
            consumer.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static SendEventPetition handlePetition(){
        lock.lock();
        SendEventPetition sendEventPetition = null;
        try{
            while(lastElement < 0) {
                consumer.await();            
            }
            sendEventPetition = petitions.get(lastElement);
            petitions.remove(lastElement);
            --lastElement;
            producer.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return sendEventPetition;
    }

    public static class SendEventPetition{
        private String streamName;
        private Object[] event;


        public SendEventPetition(String streamName, Object[] event) {
            this.streamName = streamName;
            this.event = event;
        }

        public String getStreamName() {
            return streamName;
        }

        public void setStreamName(String streamName) {
            this.streamName = streamName;
        }

        public Object[] getEvent() {
            return event;
        }

        public void setEvent(Object[] event) {
            this.event = event;
        }
    }
}
