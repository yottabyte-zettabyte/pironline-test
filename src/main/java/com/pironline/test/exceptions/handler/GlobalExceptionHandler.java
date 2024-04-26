package com.pironline.test.exceptions.handler;

import com.pironline.test.exceptions.BadRequestException;
import com.pironline.test.exceptions.GenericException;
import com.pironline.test.exceptions.NotFoundException;
import com.pironline.test.locale.LocalizedMessageSource;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private LocalizedMessageSource messageSource;

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatusCode status,
                                                               WebRequest request) {
        List<String> validationErrors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                validationErrors.add(String.format("%s: %s", fieldError.getField(), error.getDefaultMessage()));
            }
        }

        log.error("Error while validating input data: {}", validationErrors);
        String localizedErrorMessage = messageSource.getMessage(ErrorCode.ERROR_INVALID_DATA.getValue());
        return new ResponseEntity(new ErrorMessage(ErrorCode.ERROR_INVALID_DATA, localizedErrorMessage), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatusCode status,
                                                               WebRequest request) {
        log.error("Error while trying to parse input data: ", ex);
        return this.handleExceptionInternal(ex, "JSON cannot be read", headers, status, request);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                       HttpHeaders headers, HttpStatusCode status,
                                                                       WebRequest request) {
        String errorMsg = messageSource.getMessage(ErrorCode.ERROR_EMPTY_NAMED_PARAM.getValue(), new Object[] {ex.getParameterName()});
        log.error("Error while trying to parse input params: {}", errorMsg);
        return this.handleExceptionInternal(ex, errorMsg, headers, status, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleException(NotFoundException ex) {
        return createErrorMessage(ex);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(BadRequestException ex) {
        return createErrorMessage(ex);
    }

    @ExceptionHandler(value = GenericException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(GenericException ex) {
        return createErrorMessage(ex);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(ErrorCode.ERROR_GENERIC, messageSource.getMessage(ErrorCode.ERROR_GENERIC.getValue()));
    }

    private ErrorMessage createErrorMessage(GenericException ex) {
        String localizedErrorMessage = messageSource.getMessage(ex.getErrorCode().getValue(), ex.getArgs());
        log.error(localizedErrorMessage, ex);
        return new ErrorMessage(ex.getErrorCode(), localizedErrorMessage);
    }
}
