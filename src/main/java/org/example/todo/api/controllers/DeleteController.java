package org.example.todo.api.controllers;

import io.qameta.allure.Step;
import okhttp3.Headers;
import okhttp3.Response;
import org.example.todo.api.HttpClient;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class DeleteController extends BaseController {
    public DeleteController(HttpClient httpClient, Headers headers) {
        super(httpClient, headers);
    }

    @Step("Отправить данные на тачку {0}")
    public Response deleteTodo(String endpoint, int id) {
        Response response = httpClient.sendDelete(endpoint, id);
        return response;
    }

    @Step("Отправить некорректные данные на тачку {0}")
    public Response sendWrongDelete(String endpoint, String id, String cred) {
        Response response = httpClient.sendWrongDelete(endpoint, id, cred);
        return response;
    }
}