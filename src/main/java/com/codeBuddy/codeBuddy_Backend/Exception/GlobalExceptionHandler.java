package com.codeBuddy.codeBuddy_Backend.Exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<APIExceptionResponse> buildResponse(
            Exception ex, HttpStatus status, String path
    ){
        APIExceptionResponse body=new APIExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                path
        );

        return new ResponseEntity<>(body,status);
    }

//-------Custom Exception-----------
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIExceptionResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req){
        System.out.println(ex);
        return buildResponse(ex,HttpStatus.NOT_FOUND, req.getRequestURI());
    }

    @ExceptionHandler(BadRequestException.class)
    public  ResponseEntity<APIExceptionResponse> handleBadRequest(BadRequestException ex,HttpServletRequest req){
        System.out.println(ex);
        return buildResponse(ex,HttpStatus.BAD_REQUEST,req.getRequestURI());
    }

// ---------------- Framework Related Exceptions ----------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIExceptionResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest req) {

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Validation failed");

        return buildResponse (new RuntimeException(errorMessage), HttpStatus.BAD_REQUEST, req.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIExceptionResponse> handleDatabaseErrors(
            DataIntegrityViolationException ex, HttpServletRequest req) {
        return buildResponse(new RuntimeException("Database constraint error"), HttpStatus.CONFLICT, req.getRequestURI());
    }


// ---------------- Fallback for Unexpected Errors ----------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIExceptionResponse> handleGeneric(
            Exception ex, HttpServletRequest req) {

        ex.printStackTrace(); // Log for debugging or use Logger

        return buildResponse(
                new RuntimeException("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR,
                req.getRequestURI()
        );
    }
}



