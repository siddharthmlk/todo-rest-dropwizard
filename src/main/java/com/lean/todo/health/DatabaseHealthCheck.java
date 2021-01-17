package com.lean.todo.health;

import com.codahale.metrics.health.HealthCheck;
import com.lean.todo.service.TodoService;

public class DatabaseHealthCheck extends HealthCheck {
    private final TodoService todoService;

    public DatabaseHealthCheck(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    protected Result check() throws Exception {
        String result = todoService.performHealthCheck();
        if(result==null) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Service is not healthy:: "+result);
        }
    }
}
