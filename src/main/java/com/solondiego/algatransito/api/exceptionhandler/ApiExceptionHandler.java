package com.solondiego.algatransito.api.exceptionhandler;

import com.solondiego.algatransito.domain.exception.EntidadeNaoEncontradaException;
import com.solondiego.algatransito.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estão inválidos");
        problemDetail.setType(URI.create("https://algatransito.com/erros/campos-invalidos"));

        //ex.getBindingResult().getAllErrors().stream().forEach(objectError -> System.out.println(((FieldError) objectError).getField() + " - " + objectError.getDefaultMessage()));

        //Map<String, String> fields = ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(), DefaultMessageSourceResolvable::getDefaultMessage));

        Map<String, String> fields = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(objectError -> ((FieldError) objectError)
                        .getField(), objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())));

        problemDetail.setProperty("fields", fields);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

//    @ExceptionHandler
//    public ResponseEntity<String> capturar(NegocioException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(NegocioException.class)
    public ProblemDetail handleNegocio(NegocioException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algatransito.com/erros/regra-de-negocios"));

        return problemDetail;
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handleEntidadeNaoEncontrada(NegocioException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algatransito.com/erros/nao-encontrado"));

        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Recurso está em uso");
        problemDetail.setType(URI.create("https://algatransito.com/erros/recurso-esta-em-uso"));

        return problemDetail;
    }

}
