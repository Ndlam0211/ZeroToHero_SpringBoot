package com.lamnd.zerotohero.exception;

import com.lamnd.zerotohero.dto.reponse.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleOtherException(Exception ex){
        APIResponse response = new APIResponse();

        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIResponse> handleAppException(AppException ex){
        APIResponse response = new APIResponse();

        response.setCode(ex.getErrorCode().getCode());
        response.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String,String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        APIResponse<Map<String,String>> response = new APIResponse<>();

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName,errorMessage);
        });

        response.setCode(ErrorCode.INVALID_DATA.getCode());
        response.setMessage(ErrorCode.INVALID_DATA.getMessage());
        response.setData(errors);

        return ResponseEntity.badRequest().body(response);
    }
}
