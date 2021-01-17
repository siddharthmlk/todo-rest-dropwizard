package com.lean.todo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lean.todo.TodoMainApplication;
import com.lean.todo.TodoMainConfiguration;
import com.lean.todo.api.TodoDTO;
import com.lean.todo.api.TodoTasksDTO;
import com.lean.todo.service.TodoService;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TodoResourceTest {

    private static final TodoService mockService = Mockito.mock(TodoService.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new TodoResource(mockService)).build();
    private TodoDTO todoRequest;
//    private static DropwizardAppExtension<TodoMainConfiguration> EXT =
//            new DropwizardAppExtension<>(TodoMainApplication.class,
//                    ResourceHelpers.resourceFilePath("todo.yml"));

    @BeforeEach
    void setup() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Ignore
    public void testGetTodos() {
        final TodoDTO todoDTO = new TodoDTO();
        todoDTO.setName("ABC");
        final TodoTasksDTO todoTasksDTO = new TodoTasksDTO();
        todoDTO.setTasks(Arrays.asList(todoTasksDTO));
        final List<TodoDTO> todoDTOS = Arrays.asList(todoDTO);

        when(mockService.getAll()).thenReturn(todoDTOS);

        final List<TodoDTO> response = EXT.target("todos/v1/").request().get(List.class);

        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getName()).isEqualTo("ABC");
        verify(mockService).getAll();

    }

    @Test
    @Ignore
    public void testGetTodoByIdWhenNotFound() {
        when(mockService.findById(2l)).thenReturn(null);

        Response response = EXT.target("todos/v1/2").request().get();
        assertThat(response.getStatusInfo().getStatusCode()).
                isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(mockService).findById(2L);
    }

    @Test
    @Ignore
    public void testCreateTodo() {
        final TodoDTO todoRequest = new TodoDTO();
        final TodoTasksDTO todoTasksDTO = new TodoTasksDTO();
        todoRequest.setTasks(Arrays.asList(todoTasksDTO));
        final TodoDTO todoDTO = new TodoDTO();
        final TodoTasksDTO todoTasksDTO1 = new TodoTasksDTO();
        todoDTO.setTasks(Arrays.asList(todoTasksDTO1));

        when(mockService.create(any(TodoDTO.class))).thenReturn(todoRequest);

        Response response = EXT.target("todos/v1/").request().post(Entity.json(todoRequest));
        assertThat(response.getStatus()).isEqualTo(200);
    }
}
