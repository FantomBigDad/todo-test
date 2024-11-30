package org.example.todo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import okhttp3.Response;
import org.example.todo.model.ResponseBody;
import org.example.todo.model.ResponseWrapper;


import java.io.IOException;
import java.util.List;

public class MappingUtils {
    public <T> ResponseWrapper<List<T>> parseResponse(Response response, Class<T> clazz) {
        ResponseWrapper<List<T>> agentResponseWrapper = new ResponseWrapper<>();
        try {
            String json = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();

            // Обработка массива JSON
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            List<T> body = objectMapper.readValue(json, listType);

            agentResponseWrapper.setCode(response.code());
            agentResponseWrapper.setWrapperBody(new ResponseBody<>());
            agentResponseWrapper.getWrapperBody().setBody(body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return agentResponseWrapper;
    }

    public ResponseWrapper<String> parseErrorResponse(Response response) {
        ResponseWrapper<String> agentResponseWrapper = new ResponseWrapper<>();
        try {
            String errorMessage = response.body().string();

            ResponseBody<String> responseBody = new ResponseBody<>();
            responseBody.setBody(errorMessage);

            agentResponseWrapper.setCode(response.code());
            agentResponseWrapper.setWrapperBody(responseBody);

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse error response", e);
        }
        return agentResponseWrapper;
    }
}
