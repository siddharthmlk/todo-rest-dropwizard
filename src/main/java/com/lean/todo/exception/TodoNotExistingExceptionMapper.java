package com.lean.todo.exception;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TodoNotExistingExceptionMapper implements ExceptionMapper<TodoNotExistingException> {
    private final Meter exceptions;
    public TodoNotExistingExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(MetricRegistry.name(getClass(), "exceptions"));
    }

    @Override
    public Response toResponse(TodoNotExistingException exception) {
        exceptions.mark();
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),
                        exception.getMessage()))
                .build();
    }
}
