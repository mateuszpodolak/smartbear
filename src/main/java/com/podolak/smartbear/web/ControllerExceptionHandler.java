package com.podolak.smartbear.web;

import com.podolak.smartbear.dto.ErrorDto;
import com.podolak.smartbear.exception.InvalidTimeInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidTimeInputException.class)
    protected ResponseEntity<ErrorDto> handleInvalidTimeInputException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

}
