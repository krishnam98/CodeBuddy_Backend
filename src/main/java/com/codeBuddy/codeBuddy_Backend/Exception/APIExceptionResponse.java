package com.codeBuddy.codeBuddy_Backend.Exception;


import java.time.LocalDateTime;

public record APIExceptionResponse (
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path
){}

