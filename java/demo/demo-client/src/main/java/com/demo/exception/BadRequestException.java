package com.demo.exception;

import com.demo.util.ClientLogUtils;

public class BadRequestException extends Exception {
    private static final long serialVersionUID = 5235345858137787048L;

    private int               errorCode        = 0;

    private String            description;

    public BadRequestException(int errorCode, String description) {
        super(String.format("Request Error with id:%d, description:%s", errorCode, description));
        this.errorCode = errorCode;
        this.description = description;
    }

    public BadRequestException(int errorCode, String description, Throwable cause) {
        super(String.format("Request Error with id:%d, description:%s", errorCode, description), cause);
        this.errorCode = errorCode;
        this.description = description;
    }

    public BadRequestException(String message) {
        super(message);
        this.description = message;
    }

    public BadRequestException(String message, Throwable cause, boolean isPrintLog) {
        super(message, cause);
        this.description = message;

        if (isPrintLog) {
            ClientLogUtils.saveLogToFile(cause);
        }
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

}
