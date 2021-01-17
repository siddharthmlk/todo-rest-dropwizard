package com.lean.todo;

import com.codahale.metrics.health.HealthCheck;
import com.lean.todo.core.Todo;
import com.lean.todo.core.TodoTask;
import com.lean.todo.db.TodoDAO;
import com.lean.todo.exception.TodoNotExistingExceptionMapper;
import com.lean.todo.health.DatabaseHealthCheck;
import com.lean.todo.resources.TodoResource;
import com.lean.todo.service.TodoService;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TodoMainApplication extends Application<TodoMainConfiguration> {

    public static void main(String[] args) throws Exception {
        new TodoMainApplication().run(args);
    }

    @Override
    public void run(TodoMainConfiguration todoMainConfiguration, Environment environment) throws Exception {
        TodoDAO todoDao = new TodoDAO(hibernate.getSessionFactory());
        TodoService todoService = new UnitOfWorkAwareProxyFactory(hibernate).create(TodoService.class, TodoDAO.class, todoDao);
        environment.jersey().register(new TodoResource(todoService));
        environment.jersey().register(new TodoNotExistingExceptionMapper(environment.metrics()));
        DatabaseHealthCheck healthCheck = new DatabaseHealthCheck(todoService);
        environment.healthChecks().register("Todo Application", healthCheck);
    }


    private final HibernateBundle<TodoMainConfiguration> hibernate = new HibernateBundle<TodoMainConfiguration>(Todo.class, TodoTask.class){
        @Override
        public DataSourceFactory getDataSourceFactory(TodoMainConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<TodoMainConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }
}
