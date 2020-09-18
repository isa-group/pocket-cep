package com.siddhiApi.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiKeyAuth {
    private final String api_key;
    private static ApiKeyAuth apiKeyAuth;

    static Logger logger = LoggerFactory.getLogger(ApiKeyAuth.class);

    private ApiKeyAuth(){
        this.api_key = System.getenv("api_key");
    }

    public static ApiKeyAuth getApiKeyAuth() {
        if (apiKeyAuth == null){
            apiKeyAuth = new ApiKeyAuth();
        }
        return apiKeyAuth;
    }

    private boolean validApiKey(String api_key){
        return this.api_key.equals(api_key);
    }

    public boolean auth(String api_key){
        //return true;
        return this.api_key.equals("none") || validApiKey(api_key);
    }

    public boolean apiKeyStablished(){
        return !this.api_key.equals("none");
    }

    public String getApi_key() {
        return api_key;
    }
}
