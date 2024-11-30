package org.example.todo.model;

import lombok.Data;

@Data
public class Todo {
    private long id;
    private String text;
    private boolean completed;
}
