package com.malikov.ticketsystem.util.exception;

/**
 * @author Yurii Malikov
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
