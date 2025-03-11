package com.findjobbe.findjobbe.exception;

import java.util.Locale;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Setter
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private final MessageSource messageSource;

  @Autowired
  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  private String getMessage(String context) {
    return messageSource.getMessage(
        context + ".message", null, context, LocaleContextHolder.getLocale());
  }

  private String getErrorCode(String context) {
    return messageSource.getMessage(
        context + ".code", null, context, LocaleContextHolder.getLocale());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
    String message = getMessage(e.getMessage());
    String errorCode = getErrorCode(e.getMessage());
    return new ResponseEntity<>(new ErrorResponse(message, errorCode), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
    String message = getMessage(e.getMessage());
    String errorCode = getErrorCode(e.getMessage());
    return new ResponseEntity<>(new ErrorResponse(message, errorCode), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
    String message = getMessage(e.getMessage());
    String errorCode = getErrorCode(e.getMessage());
    return new ResponseEntity<>(new ErrorResponse(message, errorCode), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
    String message = getMessage(e.getMessage());
    String errorCode = getErrorCode(e.getMessage());
    return new ResponseEntity<>(new ErrorResponse(message, errorCode), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerErrorException(
      InternalServerErrorException e) {
    return new ResponseEntity<>(
        new ErrorResponse(e.getMessage(), "Internal_Server_Error"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    FieldError error = ex.getBindingResult().getFieldErrors().get(0);
    String objectName = error.getObjectName();
    String field = error.getField();
    String errorType = error.getCode();

    String codeKey = objectName + "." + field + "." + errorType + ".code";
    String messageKey = objectName + "." + field + "." + errorType + ".message";

    String errorCode =
        messageSource.getMessage(codeKey, null, "UNKNOWN_ERROR", Locale.getDefault());
    String errorMessage =
        messageSource.getMessage(messageKey, null, error.getDefaultMessage(), Locale.getDefault());

    return new ResponseEntity<>(new ErrorResponse(errorMessage, errorCode), HttpStatus.BAD_REQUEST);
  }
}
