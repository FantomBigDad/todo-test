package org.example.todo.api.controllers;

import io.qameta.allure.Step;
import okhttp3.Headers;
import okhttp3.Response;
import org.example.todo.api.HttpClient;
import org.example.todo.model.ResponseWrapper;
import org.json.JSONObject;

public class PostController extends BaseController {
    public PostController(HttpClient httpClient, Headers headers) {
        super(httpClient, headers);
    }

    @Step("Отправить данные на тачку {0}")
    public Response postTodo(String endpoint, JSONObject body) {
        Response response = httpClient.sendPost(endpoint, body);
        return response;
    }

    @Step("Отправить некорректные данные на тачку {0}")
    public ResponseWrapper<String> postTodoInvalid(String endpoint, JSONObject body) {
        Response response = httpClient.sendPost(endpoint, body);
        return mappingUtils.parseErrorResponse(response);
    }
}