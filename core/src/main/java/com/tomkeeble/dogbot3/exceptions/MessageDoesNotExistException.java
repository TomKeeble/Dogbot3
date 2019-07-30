package com.tomkeeble.dogbot3.exceptions;

public class MessageDoesNotExistException extends Exception{
    public MessageDoesNotExistException(String message) {
        super(message);
    }
}
