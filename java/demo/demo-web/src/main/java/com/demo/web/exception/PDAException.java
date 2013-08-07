package com.demo.web.exception;

public class PDAException extends RuntimeException {

    private static final long serialVersionUID = 5235345858137787048L;

    private int               errorCode        = 0;

    private String            description;

    public PDAException(int errorCode) {
        this.errorCode = errorCode;
    }

    public PDAException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public PDAException(int errorCode, String description) {
        super(String.format("Request Error with id:%d, description:%s", errorCode, description));
        this.errorCode = errorCode;
        this.description = description;
    }

    public PDAException(String message) {
        super(message);
    }

    public PDAException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDAException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
