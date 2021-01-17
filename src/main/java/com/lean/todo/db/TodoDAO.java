package com.lean.todo.db;

import com.lean.todo.core.Todo;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TodoDAO extends AbstractDAO<Todo> {

    public TodoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Todo> findById(Long id){
        return Optional.ofNullable(get(id));
    }

    public List<Todo> findAll(){
        return list((Query<Todo>) namedQuery("com.lean.todo.core.Todo.findAll"));
    }

    public Todo create(Todo todo){
        return persist(todo);
    }

    public Todo update(Todo todo){
        return persist(todo);
    }

    public void delete(Long id){
         currentSession().delete(get(id));
    }
}
