package com.ishingarov.person.model;

public record ErrorResponse(String message)
        implements ErrorWithMessage { }
