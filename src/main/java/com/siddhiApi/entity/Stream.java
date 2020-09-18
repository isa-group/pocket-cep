package com.siddhiApi.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class Stream {
    private String streamID;
    private JSONObject jsonSchema;

    public Stream(String streamID, Object jsonSchema) {
        this.streamID = streamID;
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.jsonSchema = new JSONObject(mapper.writeValueAsString(jsonSchema));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getStreamID() {
        return streamID;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    public JSONObject getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(JSONObject jsonSchema) {
        this.jsonSchema = jsonSchema;
    }
}
