package com.malikov.ticketsystem.util.exception;

public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String[] details;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this(url, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    public ErrorInfo(CharSequence url, String cause, String... details) {
        this.url = url.toString();
        this.cause = cause;
        this.details = details;
    }
}
