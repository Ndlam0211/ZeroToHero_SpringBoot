package com.lamnd.zerotohero.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_DATA(102, "Invalid data", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(103, "Username or password is incorrect", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(104, "Unauthorized", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(105, "You do not have permission", HttpStatus.FORBIDDEN),
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
