package com.ishingarov.person.model;

import jakarta.validation.constraints.NotNull;

public record PersonRequest(
        @NotNull String name,
        Integer age,
        String address,
        String work
) { }
