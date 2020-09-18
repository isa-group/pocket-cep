package com.siddhiApi.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parsers {
    static final Logger logger = LoggerFactory.getLogger(Parsers.class);

    public static Object[] JsonObjectToObjectArray(JSONObject jsonObject){
        logger.info("Event in parameter: " + jsonObject.toString());
        logger.info("eventToJSONObject: " + jsonObject);

        Object[] eventParsed = new Object[jsonObject.keySet().size()];
        int i = 0;
        for (String property: jsonObject.keySet()){
            //property.split("\"");
            //
            logger.info("Property: " + property);
            logger.info("Value of that property: " + jsonObject.get(property));
            eventParsed[i] = jsonObject.get(property);
            if(eventParsed[i] instanceof Integer){
                eventParsed[i] = Long.parseLong(eventParsed[i].toString());
            }
            ++i;
        }
        return eventParsed;
    }
}
