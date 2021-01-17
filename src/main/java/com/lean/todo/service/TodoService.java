package com.lean.todo.service;

import static com.lean.todo.service.DtoDomainMapper.convertToDto;
import static com.lean.todo.service.DtoDomainMapper.convertToEntity;

import com.lean.todo.api.TodoDTO;
import com.lean.todo.core.Todo;
import com.lean.todo.db.TodoDAO;
import com.lean.todo.exception.TodoNotExistingException;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoService {

    private TodoDAO todoDAO;

    public TodoService(TodoDAO todoDAO){
        this.todoDAO = todoDAO;
    }

    @UnitOfWork
    public List<TodoDTO> getAll(){
        List<Todo> todos = todoDAO.findAll();
        if(null != todos){
            return todos.stream().map(todo -> DtoDomainMapper.convertToDto(todo))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @UnitOfWork
    public TodoDTO findById(Long id) {
        Optional<Todo> todo = todoDAO.findById(id);
        if(!todo.isPresent()){
            throw new TodoNotExistingException(String.format("Existing todo not found with id %d",id));
        }
        return convertToDto(todo.get());
    }

    @UnitOfWork
    public TodoDTO create(TodoDTO todoRequest){
        Todo savedTodo = todoDAO.create(convertToEntity(todoRequest, null));
        return convertToDto(savedTodo);
    }

    @UnitOfWork
    public TodoDTO update(TodoDTO todoRequest, Long id){
        Optional<Todo> existingTodo = todoDAO.findById(id);
        if(!existingTodo.isPresent()){
            throw new TodoNotExistingException(String.format("Existing todo not found with id %d",id));
        }
        Todo updatedTodo = todoDAO.update(convertToEntity(todoRequest, existingTodo.get()));
        return convertToDto(updatedTodo);
    }

    @UnitOfWork
    public void delete(Long id) {
        Optional<Todo> existingTodo = todoDAO.findById(id);
        if(!existingTodo.isPresent()){
            throw new TodoNotExistingException(String.format("Existing todo not found with id %d",id));
        }
        todoDAO.delete(id);
    }

    @UnitOfWork
    public String performHealthCheck(){
        String result = null;
        try {
            todoDAO.findAll();
        } catch (Exception ex){
            result="Database unexpected error "+ex.getCause().getLocalizedMessage();
        }
        return  result;
    }
}
