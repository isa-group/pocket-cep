package com.siddhiApi.util;

import com.siddhiApi.entity.Stream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StreamGeneratorSiddhi implements StreamGenerator{

    Logger logger = LoggerFactory.getLogger(StreamGeneratorSiddhi.class);

    public static Map<String, String> jsonPropertyToSiddhiProperty= new HashMap<String, String>(){
        {
            put("number", "double");
            put("string", "string");
            //put("integer", "int");
            put("integer", "long");
            put("boolean", "boolean");
        }
    };

    @Override
    public String generateCodeInputStream(Stream stream) {
        String codeStream = "define stream " + stream.getStreamID() + "(";
        codeStream += getAllSiddhiProperties(stream.getJsonSchema().get("properties").toString());
        codeStream += ");";

        return codeStream;
    }

    @Override
    public String generateCodeOutputStream(Stream stream) {
        return " insert into " + stream.getStreamID() + ";";
    }

    private String getAllSiddhiProperties(String propertiesInJsonSchema){
        String properties = "";
        Boolean first = true;
        JSONObject schemaToStream = new JSONObject(propertiesInJsonSchema);
        for(String key : schemaToStream.keySet()){
            if (!first){
                properties += ", ";
            }
            String[] parts = schemaToStream.get(key).toString().split("\"");
            properties += key + " " + jsonPropertyToSiddhiProperty.get(parts[3]);
            first = false;
        }
        return properties;
    }
}
