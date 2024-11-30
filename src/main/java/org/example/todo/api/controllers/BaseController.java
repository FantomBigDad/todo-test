package org.example.todo.api.controllers;
import okhttp3.Headers;
import org.example.todo.api.HttpClient;
import org.example.todo.utils.MappingUtils;


public class BaseController {
    protected HttpClient httpClient;
    protected Headers headers;
    protected MappingUtils mappingUtils;

    public BaseController(HttpClient httpClient, Headers headers) {
        this.httpClient = httpClient;
        this.headers = headers;
        this.mappingUtils = new MappingUtils();
    }
}