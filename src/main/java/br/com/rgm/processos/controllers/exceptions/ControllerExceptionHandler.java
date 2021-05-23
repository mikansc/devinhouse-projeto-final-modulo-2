package br.com.rgm.processos.controllers.exceptions;

import br.com.rgm.processos.services.exceptions.InactiveObjectException;
import br.com.rgm.processos.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(status.value(), exception.getMessage(), System.currentTimeMillis(),
                null);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InactiveObjectException.class)
    public ResponseEntity<StandardError> objectNotFound(InactiveObjectException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(status.value(), exception.getMessage(), System.currentTimeMillis(),
                null);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> objectNotFound(DataIntegrityViolationException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError error = new StandardError(status.value(), "Operação não permitida. Contacte o administrador do sistema.", System.currentTimeMillis(),
                null);

        return ResponseEntity.status(status).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        StandardError error = new StandardError(status.value(), "Um ou mais campos possuem um erro",
                System.currentTimeMillis(), null);
        new Locale("pt-BR");
        List<StandardError.Campo> campos = exception.getBindingResult().getAllErrors().stream()
                .map(ex -> new StandardError.Campo(((FieldError) ex).getField(),
                        messageSource.getMessage(ex, LocaleContextHolder.getLocale())))
                .collect(Collectors.toList());
        error.setFields(campos);
        return super.handleExceptionInternal(exception, error, headers, status, request);
    }
}
