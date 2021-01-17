package com.lean.todo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.lean.todo.api.TodoDTO;
import com.lean.todo.api.TodoTasksDTO;
import com.lean.todo.core.Todo;
import com.lean.todo.core.TodoTask;
import com.lean.todo.db.TodoDAO;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hamcrest.CoreMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TodoServiceTest {
    SessionFactory sessionFactory;
    public DAOTestExtension database = DAOTestExtension.newBuilder().addEntityClass(Todo.class).
            addEntityClass(TodoTask.class).build();

    private TodoDAO todoDAO;

    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        todoDAO = new TodoDAO(database.getSessionFactory());
        sessionFactory = database.getSessionFactory();
    }

    @Test
    @Ignore
    public void testCreate() {
        Todo todoEntity = new Todo();
        todoEntity.setName("ABC");
        Todo persisted = database.inTransaction(() -> {
           return todoDAO.create(todoEntity);
        });
    assertThat(persisted.getId(), CoreMatchers.equalTo(1));

    }
}
