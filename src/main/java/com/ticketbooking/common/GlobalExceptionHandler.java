package com.ticketbooking.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ticketbooking.match.MatchNotFoundException;
import com.ticketbooking.ticket.TicketNotAvailableException;
import com.ticketbooking.ticket.TicketNotFoundException;
import com.ticketbooking.ticketitem.TicketItemNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ TicketNotFoundException.class, MatchNotFoundException.class,
      TicketItemNotFoundException.class })
  public ResponseEntity<ResultMessage<Void>> handleNotFound(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResultUtil.error(404, e.getMessage()));
  }

  @ExceptionHandler(TicketNotAvailableException.class)
  public ResponseEntity<ResultMessage<Void>> handleConflict(TicketNotAvailableException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ResultUtil.error(409, e.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResultMessage<Void>> handleBadRequest(IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultUtil.error(400, e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResultMessage<Void>> handleValidation(MethodArgumentNotValidException e) {
    FieldError fieldError = e.getBindingResult().getFieldError();
    String message = fieldError != null ? fieldError.getDefaultMessage() : "Invalid request";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultUtil.error(400, message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResultMessage<Void>> handleUnexpected(Exception e) {
    log.error("Unhandled exception", e);
    return ResponseEntity.internalServerError().body(ResultUtil.error(500, "Internal server error"));
  }
}
