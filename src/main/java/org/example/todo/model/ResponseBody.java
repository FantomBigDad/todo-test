package org.example.todo.model;


import lombok.Data;

@Data
public class ResponseBody<T> {
    private T body;
}