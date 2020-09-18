package com.siddhiApi.util;

import com.siddhiApi.entity.Pattern;
import com.siddhiApi.entity.Stream;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.exceptions.PropertyNotFoundOnSelect;
import com.siddhiApi.services.StreamService;
import com.siddhiApi.services.StreamServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PriorityQueue;
import java.util.regex.Matcher;

public class PatternCodeChecker {

    static Logger logger = LoggerFactory.getLogger(PatternCodeChecker.class);

    private static final StreamService streamService = new StreamServiceImpl();

    private static final PropertyOrderedDatabase propertyOrderedDatabase = PropertyOrderedDatabase.getPropertyOrderedDatabase();

    public static void outputStreamCheck(Pattern pattern) throws NotFoundException, PropertyNotFoundOnSelect {
        String lastControlStatement;
        if(java.util.regex.Pattern.compile("(group by)").matcher(pattern.getPatternCode()).find()){
            lastControlStatement = "group by";
        } else {
            lastControlStatement = "insert into";
        }

        Stream stream = streamService.getStream(pattern.getOutputStreamName());
        assert stream != null;
        PriorityQueue<PropertyFirstAppearance> propertyFirstAppearancePriorityQueue = new PriorityQueue<>();

        JSONObject propertiesJSON = new JSONObject(stream.getJsonSchema().get("properties").toString());
        Matcher matcher;
        for (String property : propertiesJSON.keySet()){
            matcher = java.util.regex.Pattern.compile("select.*(" + property +").*" + lastControlStatement)
                    .matcher(pattern.getPatternCode());
            logger.info("Pattern: " + "select.*(" + property +").*" + lastControlStatement);
            if(matcher.find()){
                int firstMatch = matcher.start(1); //By writing 1 here, we achieve that JAVA only looks for what it is between the parenthesis.
                propertyFirstAppearancePriorityQueue.add(new PropertyFirstAppearance(property, firstMatch));
            } else {
                throw new PropertyNotFoundOnSelect("The property " + property + " does not appear on the select statement.");
            }
        }

        String[] propertiesSorted = new String[propertyFirstAppearancePriorityQueue.size()];
        logger.info("Priority Queue Size: " + propertyFirstAppearancePriorityQueue.size());

        int i = 0;
        while(propertyFirstAppearancePriorityQueue.size() != 0){
            PropertyFirstAppearance property = propertyFirstAppearancePriorityQueue.poll();
            logger.info("Property extracted firstAppearance: " + property.getFirstAppearance());
            logger.info("Property value: " + property.getProperty());
            propertiesSorted[i] = property.getProperty();
            ++i;
        }
        propertyOrderedDatabase.addOutputStreamPropertiesOrdered(pattern.getOutputStreamName(), propertiesSorted);
    }

    public static void removePropertyOrderedInstance(String patternName) {
        propertyOrderedDatabase.removeOutputStreamPropertiesOrdered(patternName);
    }
}
