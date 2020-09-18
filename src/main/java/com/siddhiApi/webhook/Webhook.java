package com.siddhiApi.webhook;

import com.siddhiApi.authorization.ApiKeyAuth;
import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;

public class Webhook{
    private final String url;
    private final String method;
    private final String body;
    private static final ApiKeyAuth apiKeyAuth = ApiKeyAuth.getApiKeyAuth();

    static AsyncHttpClient asyncClient = Dsl.asyncHttpClient();

    Logger logger = LoggerFactory.getLogger(Webhook.class);

    public Webhook(String url, String method, String body) throws MalformedURLException {
        this.url = url;
        this.method = method;
        this.body = body;

        OutputStream os = new ByteArrayOutputStream();
        byte[] input = null;
        try {
            input = this.body.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert input != null;

        Request request;

        if (apiKeyAuth.apiKeyStablished()){
            request = new RequestBuilder()
                    .setUrl(this.url)
                    .setMethod(this.method)
                    .setBody(this.body)
                    .setHeader("Content-Type", "application/json")
                    .setHeader("X-API-Key", apiKeyAuth.getApi_key())
                    .build();
        } else {
            request = new RequestBuilder()
                    .setUrl(this.url)
                    .setMethod(this.method)
                    .setBody(this.body)
                    .setHeader("Content-Type", "application/json")
                    .build();
        }

        asyncClient.executeRequest(request, new AsyncCompletionHandler<Object>() {
            @Override
            public Object onCompleted(Response response) throws Exception {
                logger.info("Inside execute request.");
                return null;
            }
        });

        logger.info("URL: " + this.url);
        logger.info("Method: " + this.method);
        logger.info("Body: " + this.body);
    }
}
