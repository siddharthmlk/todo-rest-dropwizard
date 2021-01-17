package com.lean.todo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TodoDefinition {
    @JsonProperty
    private Long id;

    @JsonProperty
    @NotEmpty
    @NotBlank
    private String name;

    @JsonProperty
    private String description;
}
