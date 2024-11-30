package org.example.todo.api.controllers;

import io.qameta.allure.Step;
import okhttp3.Headers;
import okhttp3.Response;
import org.example.todo.api.HttpClient;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;

import java.util.List;

public class GetController extends BaseController {
    public GetController(HttpClient httpClient, Headers headers) {
        super(httpClient, headers);
    }

    @Step("Получить информацию {0}")
    public ResponseWrapper<List<Todo>> takeTodo(String endpoint) {
        Response response = httpClient.sendGet(endpoint);
        return mappingUtils.parseResponse(response, Todo.class);
    }

    @Step("Получить информацию {0} с текстом ошибки")
    public ResponseWrapper<String> takeTodoInvalid(String endpoint) {
        Response response = httpClient.sendGet(endpoint);
        return mappingUtils.parseErrorResponse(response);
    }
}
