package org.example.todo.api;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.example.todo.config.Config;
import org.example.todo.utils.JsonUtils;


import java.io.File;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


public class HttpClient {
    private final String baseUrl;
    private final OkHttpClient httpClient;
    private Headers headers;

    /***
     * @param baseUrl базовый URL для всех запросов данного объекта.
     */
    public HttpClient(String baseUrl, Headers headers) {
        this.baseUrl = baseUrl;
        this.headers = headers;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        this.httpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }


    public Response sendGet(String endPoint) {
        Request request = new Request.Builder()
                .url(baseUrl + endPoint)
                .headers(headers)
                .build();

        return sendRequest(request);
    }

    public <B> Response sendPost(String endPoint, B body) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonBody = new JsonUtils().getJsonStringFromObject(body);
        RequestBody requestBody = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(baseUrl + endPoint)
                .headers(headers)
                .post(requestBody)
                .build();

        return sendRequest(request);
    }

    public <B> Response sendPut(String endPoint, B body) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonBody = new JsonUtils().getJsonStringFromObject(body);
        RequestBody requestBody = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(baseUrl + endPoint)
                .headers(headers)
                .put(requestBody)
                .build();

        return sendRequest(request);
    }


    public Response sendDelete(String endPoint, int id) {
        String encodedCredentials = Base64.getEncoder().encodeToString(Config.get("AUTH_CRED").getBytes());
        Request request = new Request.Builder()
                .url(baseUrl + endPoint + "/" + id)
                .headers(headers)
                .addHeader("Authorization", "Basic "+ encodedCredentials)
                .delete()
                .build();

        return sendRequest(request);
    }

    public Response sendWrongDelete(String endPoint, String id, String wrongCred) {
        String encodedCredentials = Base64.getEncoder().encodeToString(wrongCred.getBytes());
        Request request = new Request.Builder()
                .url(baseUrl + endPoint + "/" + id)
                .headers(headers)
                .addHeader("Authorization", "Basic "+ encodedCredentials)
                .delete()
                .build();

        return sendRequest(request);
    }

    private Response sendRequest(Request request) {
        try {
            return httpClient.newCall(request).execute();
        } catch (Exception e) {
            new RuntimeException("Ошибка во время отправки запроса");
        }
        return null;
    }
}