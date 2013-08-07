package com.demo.exception;

public class LoginInvalidException extends RuntimeException {
    private static final long serialVersionUID = 5235345858137787048L;

    private int               errorCode        = 0;

    private String            description;

    public LoginInvalidException(int errorCode, String description) {
        super(String.format("Request Error with id:%d, description:%s", errorCode, description));
        this.errorCode = errorCode;
        this.description = description;
    }

    public LoginInvalidException(int errorCode, String description, Throwable cause) {
        super(String.format("Request Error with id:%d, description:%s", errorCode, description), cause);
        this.errorCode = errorCode;
        this.description = description;
    }

    public LoginInvalidException(String message) {
        super(message);
        this.description = message;
    }

    public LoginInvalidException(String message, Throwable cause) {
        super(message, cause);
        this.description = message;
    }

    public LoginInvalidException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

}
