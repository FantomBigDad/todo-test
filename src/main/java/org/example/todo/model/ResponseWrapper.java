package org.example.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper<T> {
    private int code;
    private ResponseBody<T> wrapperBody;
}