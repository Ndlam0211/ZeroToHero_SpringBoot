package com.lamnd.zerotohero.exception;

public enum ErrorCode {
    USER_EXISTED(101, "User already exists"),
    INVALID_DATA(101, "Invalid data"),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
