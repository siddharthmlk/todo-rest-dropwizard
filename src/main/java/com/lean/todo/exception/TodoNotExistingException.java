package com.lean.todo.exception;

public class TodoNotExistingException extends RuntimeException{

    public TodoNotExistingException(String msg){
        super(msg);
    }
}
