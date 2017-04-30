package com.malikov.lowcostairline.context.exceptions;

/**
 * @author Yurii Malikov
 */
public class ContextException extends RuntimeException {
    public ContextException(Throwable cause) {
        super(cause);
    }

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
