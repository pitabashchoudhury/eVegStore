package com.vegstore.user_service.exception;

public class EmailAlreadyExistsException  extends  RuntimeException{

    public EmailAlreadyExistsException(String message){

        super(message);
    }
}
