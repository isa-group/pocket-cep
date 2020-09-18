package com.siddhiApi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PropertyOrderedDatabase {
    Logger logger = LoggerFactory.getLogger(PropertyOrderedDatabase.class);

    private final Map<String, String[]> outputStreamPropertiesOrdered;
    private static PropertyOrderedDatabase propertyOrderedDatabase;

    private PropertyOrderedDatabase() {
        this.outputStreamPropertiesOrdered = new HashMap<>();
    }

    public static PropertyOrderedDatabase getPropertyOrderedDatabase() {
        if(propertyOrderedDatabase == null){
            propertyOrderedDatabase = new PropertyOrderedDatabase();
        }
        return propertyOrderedDatabase;
    }

    public void addOutputStreamPropertiesOrdered(String outputStreamName, String[] propertiesOrdered){
        logger.info("Name of the output stream: " + outputStreamName);
        outputStreamPropertiesOrdered.put(outputStreamName, propertiesOrdered);
    }

    public void removeOutputStreamPropertiesOrdered(String outputStreamName){
        outputStreamPropertiesOrdered.remove(outputStreamName);
    }

    public String[] getPropertiesOrdered(String outputStreamName){
        logger.info("Name of the output stream: " + outputStreamName);
        return outputStreamPropertiesOrdered.get(outputStreamName);
    }
}
