package com.aykhan.streamapi.exception;

import com.aykhan.streamapi.dto.ErrorResponseDTO;
import com.aykhan.streamapi.exception.notfound.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = NotFound.class)
    ResponseEntity<ErrorResponseDTO> notFound(NotFound e) {
        ErrorResponseDTO res = ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .status(404)
                .build();
        return ResponseEntity
                .status(NOT_FOUND)
                .body(res);
    }
}
