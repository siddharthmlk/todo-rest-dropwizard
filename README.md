## TODO REST API using Dropwizard

A simple todo application implemented using Dropwizard framework.
It exposes _todo_ resource with following endpoints:

* GET /todos → Returns a list of all Todos
* POST /todos → Creates a Todo and returns a Todo with id
* GET /todos/{id} → Returns a Todo
* PUT /todos/{id} → Overwrites an existing Todo
* DELETE /todos/{id} → Deletes a Todo

JSON structure of a Todo request is as follows:
```shell
{
id [mandatory]
name [mandatory]
description
tasks: [
{
id [mandatory]
name [mandatory]
description
}
]
}
```

## How to run
* Java 8 is needed to run this application
* Clone the repository
* Build the application
```shell
mvn clean install
```
* To run the server
```shell
java -jar target/TodoApplication-1.0-SNAPSHOT.jar server todo.yml 
```
* Endpoints can be accessed using
  
  http://localhost:8080/todos
  

* Admin operational menu can be accessed using

  http://localhost:8081
  

## Things remaining to be done
* Test cases for resources using in-memory Jersey server and database interactions are yet to be fixed
* Exhaustive test cases implementation is pending
* Logging/Authentication not implemented
