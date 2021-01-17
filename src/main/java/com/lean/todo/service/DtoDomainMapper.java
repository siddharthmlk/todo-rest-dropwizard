package com.lean.todo.service;

import com.lean.todo.api.TodoDTO;
import com.lean.todo.api.TodoTasksDTO;
import com.lean.todo.core.Todo;
import com.lean.todo.core.TodoTask;

import java.util.ArrayList;
import java.util.List;

public class DtoDomainMapper {

    public static TodoDTO convertToDto(Todo todo){
        TodoDTO todoDto = new TodoDTO();
        todoDto.setId(todo.getId());
        todoDto.setName(todo.getName());
        todoDto.setDescription(todo.getDescription());
        List<TodoTask> tasks = todo.getTasks();
        if(null != tasks){
            List<TodoTasksDTO> tasksDto = new ArrayList<>();
            tasks.forEach(task -> {
                TodoTasksDTO taskDTO = new TodoTasksDTO();
                taskDTO.setId(task.getId());
                taskDTO.setName(task.getName());
                taskDTO.setDescription(task.getDescription());
                tasksDto.add(taskDTO);
            });
            todoDto.setTasks(tasksDto);
        }
        return  todoDto;
    }

    public static Todo convertToEntity(TodoDTO todoRequest, Todo existingEntity) {
        Todo todo = (null!= existingEntity)? existingEntity: new Todo();
        todo.getTasks().clear();
        todo.setName(todoRequest.getName());
        todo.setDescription(todoRequest.getDescription());
        List<TodoTasksDTO> taskDto = todoRequest.getTasks();
        if(null != taskDto){
            List<TodoTask> tasks = new ArrayList<>();
            taskDto.forEach(todoTasksDTO -> {
                TodoTask task = new TodoTask();
                task.setName(todoTasksDTO.getName());
                task.setDescription(todoTasksDTO.getDescription());
                tasks.add(task);
            });
            todo.setTasks(tasks);
        }
        return todo;
    }
}
