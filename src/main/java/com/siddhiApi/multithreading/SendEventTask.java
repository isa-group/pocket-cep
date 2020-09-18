package com.siddhiApi.multithreading;

import com.siddhiApi.dao.PatternDAO;
import com.siddhiApi.dao.PatternDAOImpl;

public class SendEventTask implements Runnable{
    static PatternDAO patternDAO = new PatternDAOImpl();
    String streamName;
    Object[] eventParsed;

    public SendEventTask(String streamName, Object[] eventParsed) {
        this.streamName = streamName;
        this.eventParsed = eventParsed;
    }

    @Override
    public void run() {
        patternDAO.sendEvent(streamName, eventParsed);
    }
}
