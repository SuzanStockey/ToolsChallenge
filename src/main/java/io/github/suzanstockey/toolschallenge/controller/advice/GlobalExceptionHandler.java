package io.github.suzanstockey.toolschallenge.controller.advice;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura a exceção de ID duplicado e retorna um HTTP 409 (Conflict).
     */
    @ExceptionHandler(TransacaoJaExistenteException.class)
    public ResponseEntity<Map<String, String>> handleTransacaoJaExistente(TransacaoJaExistenteException e) {

        Map<String, String> erro = Map.of("erro", e.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.CONFLICT);
    }

    /**
     * Captura a exceção de transação não encontrada e retorna um HTTP 404 (Not Found).
     */
    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleTransacaoNaoEncontrada(TransacaoNaoEncontradaException e) {
        Map<String, String> erro = Map.of("erro", e.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    /**
     * Captura exceções de regras de negócio (ex: estorno não permitido) e retorna um HTTP 422 (Unprocessable Entity).
     */
    @ExceptionHandler(EstornoNaoPermitidoException.class)
    public ResponseEntity<Map<String, String>> handleEstornoNaoPermitido(EstornoNaoPermitidoException e) {
        Map<String, String> erro = Map.of("erro", e.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.UNPROCESSABLE_ENTITY);
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
}
