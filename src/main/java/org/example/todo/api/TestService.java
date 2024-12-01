package org.example.todo.api;

import okhttp3.Headers;
import org.example.todo.api.controllers.DeleteController;
import org.example.todo.api.controllers.GetController;
import org.example.todo.api.controllers.PostController;
import org.example.todo.api.controllers.PutController;
import org.example.todo.config.Config;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.junit.jupiter.api.AfterAll;

import java.io.IOException;
import java.util.List;

public class TestService {

    private final HttpClient httpClient;
    private Headers headers;

    public TestService(String baseUrl) {
        headers = new Headers.Builder()
                .build();

        this.httpClient = new HttpClient(baseUrl, headers);
    }

    public GetController getController(){
        return new GetController(httpClient, headers);
    }
    public PostController postController(){return new PostController(httpClient, headers);}
    public PutController putController(){return new PutController(httpClient, headers);}

    public DeleteController deleteController(){return new DeleteController(httpClient, headers);}
}
