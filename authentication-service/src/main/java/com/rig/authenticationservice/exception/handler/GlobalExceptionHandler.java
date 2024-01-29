package com.rig.authenticationservice.exception.handler;

import com.rig.authenticationservice.data.payload.response.ExceptionResponse;
import com.rig.authenticationservice.exception.LoginException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public final class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(final BindException exception) {
        log.error("{} occurred: ", exception.getClass().getSimpleName(), exception);
        return new ExceptionResponse(extractMessage(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(final ConstraintViolationException exception) {
        log.error("{} occurred: ", exception.getClass().getSimpleName(), exception);
        return new ExceptionResponse(extractMessage(exception));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handle(final BadCredentialsException exception) {
        log.error("{} occurred: {}", exception.getClass().getSimpleName(), exception.getMessage());
        return new ExceptionResponse(
                messageSource.getMessage("exception.login", null, Locale.getDefault())
        );
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handle(final InternalAuthenticationServiceException exception) {
        log.error("{} occurred: {}", exception.getClass().getSimpleName(), exception.getMessage());
        return new ExceptionResponse(
                messageSource.getMessage("exception.login", null, Locale.getDefault())
        );
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handle(final LoginException exception) {
        log.error("{} occurred: {}", exception.getClass().getSimpleName(), exception.getMessage());
        return new ExceptionResponse(
                messageSource.getMessage("exception.login", null, Locale.getDefault())
        );
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(final NullPointerException exception) {
        log.error("{} occurred: ", exception.getClass().getSimpleName(), exception);
        return new ExceptionResponse(
                messageSource.getMessage("exception.default", null, Locale.getDefault())
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(final RuntimeException exception) {
        log.error("{} occurred: ", exception.getClass().getSimpleName(), exception);
        return new ExceptionResponse(
                messageSource.getMessage("exception.default", null, Locale.getDefault())
        );
    }

    private String extractMessage(final BindException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    private String extractMessage(final ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}
