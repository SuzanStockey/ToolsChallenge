package io.github.suzanstockey.toolschallenge.controller.advice;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura a exceção de ID duplicado e retorna um HTTP 409 (Conflict).
     */
    @ExceptionHandler(TransacaoJaExistenteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleTransacaoJaExistente(TransacaoJaExistenteException e) {

        return new ApiErrorResponse(e.getMessage());
    }

    /**
     * Captura a exceção de transação não encontrada e retorna um HTTP 404 (Not Found).
     */
    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleTransacaoNaoEncontrada(TransacaoNaoEncontradaException e) {
        return new ApiErrorResponse(e.getMessage());
    }

    /**
     * Captura exceções de regras de negócio (ex: estorno não permitido) e retorna um HTTP 422 (Unprocessable Entity).
     */
    @ExceptionHandler(EstornoNaoPermitidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrorResponse handleEstornoNaoPermitido(EstornoNaoPermitidoException e) {
        return new ApiErrorResponse(e.getMessage());
    }

    /**
     * Captura exceções e validações (@Valid) e retorna um mapa dos campos e suas respectivas mensagens de erro (HTTP 400).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura exceções de argumentos ilegais (ex: enum inválido) e retorna um HTTP 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgument(IllegalArgumentException e) {
        return new ApiErrorResponse(e.getMessage());
    }
}
