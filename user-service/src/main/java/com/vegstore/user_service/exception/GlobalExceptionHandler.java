package com.vegstore.user_service.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("Invalid ID provided: " + ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error: " + ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                ex.getMessage(),

                HttpStatus.NOT_FOUND.value(),
                "User Not Found"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                ex.getMessage(),

                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SomeThing went wrong!!!"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ApiErrorResponse> handleUserCreation(UserCreationException ex, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                ex.getMessage(),

                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "SomeThing went wrong!!!"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public  ResponseEntity<ApiErrorResponse> handleEmailExist (EmailAlreadyExistsException emailAlreadyExistsException, WebRequest request){
        ApiErrorResponse error = new ApiErrorResponse(
                emailAlreadyExistsException.getMessage(),
                HttpStatus.CONFLICT.value(),  // 409 Conflict
                "EMAIL_ALREADY_EXISTS"
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse>handleRuntime(RuntimeException runtimeException){
        ApiErrorResponse errorResponse= new ApiErrorResponse(
                runtimeException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(),"SomeThing went wrong!!!"
        );


        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

     @ExceptionHandler(UsernameNotFoundException.class)
     public ResponseEntity<ApiErrorResponse> userNameNotFound(UsernameNotFoundException usernameNotFoundException){
        ApiErrorResponse errorResponse= new ApiErrorResponse(
                usernameNotFoundException.getMessage(),HttpStatus.NOT_FOUND.value(),"Use name not available in the database!!!"
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
     }
}
