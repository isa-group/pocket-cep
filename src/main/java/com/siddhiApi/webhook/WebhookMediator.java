package com.siddhiApi.webhook;

import com.siddhiApi.entity.Stream;
import com.siddhiApi.entity.Subscription;
import com.siddhiApi.exceptions.NotFoundException;
import com.siddhiApi.services.StreamService;
import com.siddhiApi.services.StreamServiceImpl;
import com.siddhiApi.util.PropertyOrderedDatabase;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebhookMediator {
    static Logger logger = LoggerFactory.getLogger(WebhookMediator.class);

    private static StreamService streamService = new StreamServiceImpl();
    private static PropertyOrderedDatabase propertyOrderedDatabase = PropertyOrderedDatabase.getPropertyOrderedDatabase();

    private static void toUniversalSubscriptor(String streamName, String event){
        if (streamName == null) {
            streamName = "fromSubscription";
        }
        try {
            //http://localhost:8081
            //http://172.17.0.1:8081
            new Webhook("http://172.17.0.1:8081", "POST", event);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void webhookFromSiddhiApp(String streamName, Object[] data){
        String dataToJSON = dataToJSON(streamName, data);
        //toUniversalSubscriptor(streamName, dataToJSON);
        try {
            new Webhook("http://localhost:9999/api/v1/streams/" + streamName + "/events", "POST", dataToJSON);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void webhookFromSubscription(List<Subscription> subscriptions, Object event){
        for (Subscription subscription: subscriptions){
            //toUniversalSubscriptor(null, event.toString());
            try {
                new Webhook(subscription.getWebhook(), subscription.getMethod(), event.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, String> jsonPropertyToSiddhiProperty= new HashMap<String, String>(){
        {
            put("number", "double");
            put("string", "string");
            put("integer", "int");
            put("boolean", "boolean");
        }
    };

    private static String dataToJSON(String streamName, Object[] data) {
        String json = "{";
        Stream stream = null;
        try {
            stream = streamService.getStream(streamName);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        assert stream != null;
        JSONObject schemaToStream = new JSONObject(stream.getJsonSchema().get("properties").toString());

        Map<String, String> mapPropertyType = new HashMap<>();
        for(String key : schemaToStream.keySet()){
            String[] parts = schemaToStream.get(key).toString().split("\"");
            mapPropertyType.put(key, jsonPropertyToSiddhiProperty.get(parts[3]));
        }
        String[] propertiesOrdered = propertyOrderedDatabase.getPropertiesOrdered(streamName);
        for (int i = 0; i < propertiesOrdered.length; ++i){
            json +=  "\"" + propertiesOrdered[i] + "\": ";
            logger.info("Map property: " + mapPropertyType.get(propertiesOrdered[i]));
            if (mapPropertyType.get(propertiesOrdered[i]).equals("string")){
                json += "\"" + data[i].toString() + "\"";
            }
            else{
                json += data[i].toString();
            }
            if(i != propertiesOrdered.length - 1){
                json += ",";
            }
        }
        json += "}";
        logger.info("JSON created: " + json);
        return json;
    }
}
