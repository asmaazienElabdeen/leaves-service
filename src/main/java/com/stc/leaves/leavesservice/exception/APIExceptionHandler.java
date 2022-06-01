package com.stc.leaves.leavesservice.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
final class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        String detail = extractValidationDetailsFromException(ex.getBindingResult());
        String requestURI = extractRequestURI(request);
        var problem = new Problem(status, detail, requestURI);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

    @ExceptionHandler(LeaveNotFoundException.class)
    public ResponseEntity<Object> handleOrganisationNotFoundException(LeaveNotFoundException ex, WebRequest request) {
        String requestURI = extractRequestURI(request);
        var problem = new Problem(HttpStatus.NOT_FOUND, ex.getMessage(), requestURI);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleBadRequest(ResponseStatusException ex, WebRequest request) {
        String requestURI = extractRequestURI(request);
        var problem = new Problem(ex.getStatus(), ex.getReason(), requestURI);
        return ResponseEntity.status(ex.getStatus()).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String requestURI = extractRequestURI(request);
        var problem = new Problem(HttpStatus.BAD_REQUEST, ex.getMessage(), requestURI);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplicateKey(DuplicateKeyException ex, WebRequest request) {
        String requestURI = extractRequestURI(request);
        var problem = new Problem(HttpStatus.CONFLICT, ex.getCause().getMessage(), requestURI);
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String requestURI = extractRequestURI(request);
        var problem = new Problem(HttpStatus.BAD_REQUEST, ex.getMessage(), requestURI);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_PROBLEM_JSON).body(problem);
    }

    /**
     * Method used to extract the binging result message
     *
     * @param bindingResult used to get the binging result
     * @return validation message
     */
    private String extractValidationDetailsFromException(final BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()).get(0);
    }

    private String extractRequestURI(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}