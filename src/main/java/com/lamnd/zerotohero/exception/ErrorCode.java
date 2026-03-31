package com.lamnd.zerotohero.exception;

public enum ErrorCode {
    USER_EXISTED(101, "User already exists"),
    INVALID_DATA(102, "Invalid data"),
    BAD_CREDENTIALS(103, "Username or password is incorrect"),
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
