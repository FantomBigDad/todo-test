package org.example.todo.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.json.JSONObject;

import java.util.List;

public class JsonUtils {
    private ObjectMapper objectMapper;

    public JsonUtils() {
        this.objectMapper = new ObjectMapper();
    }

    public <B> String getJsonStringFromObject(B object) {
        String jsonString = object.toString();
        return jsonString;

    }

    public boolean checkJson(ResponseWrapper<List<Todo>> responseBody, int randomId, String desc, boolean type) throws JsonProcessingException {
        List<Todo> todos = responseBody.getWrapperBody().getBody();

        for (Todo item : todos) {
            if (item.getId() == randomId && item.getText().equals(desc) && item.isCompleted() == type) {
                return true;
            }
        }
        return false;
    }
}