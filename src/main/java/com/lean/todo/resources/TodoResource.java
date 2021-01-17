package com.lean.todo.resources;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import com.lean.todo.api.TodoDTO;
import com.lean.todo.service.TodoService;
import io.dropwizard.jersey.caching.CacheControl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("/todos/v1")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final TodoService todoService;

    public TodoResource(TodoService service){
        this.todoService = service;
    }

    @GET
    @Timed
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.HOURS)
    public List<TodoDTO> getTodos(){
        return todoService.getAll();
    }

    @GET
    @Timed
    @Path("/{id}")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.HOURS)
    public TodoDTO getTodoById(@PathParam("id") Long id){
        return todoService.findById(id);
    }

    @POST
    @Timed
    @Counted
    public TodoDTO createTodo(@NotNull @Valid TodoDTO todoRequest) {
        return todoService.create(todoRequest);
    }

    @PUT
    @Timed
    @Path("/{id}")
    public TodoDTO updateTodo(@PathParam("id") Long id, @NotNull @Valid TodoDTO todoRequest) {
        return todoService.update(todoRequest, id);
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public void deleteTodo(@PathParam("id") Long id) {
        todoService.delete(id);
    }
}
