package com.management.storage.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResult.getFieldErrors()
                    .add(new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return  errorResult;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    String handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        Integer index = e.getMessage().lastIndexOf(".") + 1;
        String message = "No " + e.getMessage().substring(index);
        return message;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    String handleEntityNotFoundException(EntityNotFoundException e) {
        Integer index = e.getMessage().lastIndexOf(".") + 1;
        String message = "No " + e.getMessage().substring(index);
        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    String handleConstraintViolationException(ConstraintViolationException e) {
        Integer index = e.getMessage().lastIndexOf(".") + 1;
        String message = "No " + e.getMessage().substring(index);
        return message;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StorageManagementException.class)
    @ResponseBody
    ErrorMessage handleNotFoundException(StorageManagementException e) {
        return ErrorMessage.builder()
                .name(e.name)
                .message(e.message)
                .code(e.code)
                .build();
    }

    @Getter
    @NoArgsConstructor
    static class ErrorResult {
        private final List<FieldValidationError> fieldErrors = new ArrayList<>();
        ErrorResult(String field, String message) {
            this.fieldErrors.add(new FieldValidationError(field, message));
        }
    }

    @Getter
    @AllArgsConstructor
    static class FieldValidationError {
        private String field;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    static class ErrorMessage {
        private String name;
        private String message;
        private Integer code;
    }
}
