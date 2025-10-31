package io.github.suzanstockey.toolschallenge.controller.advice;

import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
