package io.github.suzanstockey.toolschallenge.exception;

public class TransacaoJaExistenteException extends RuntimeException {
    public TransacaoJaExistenteException(String id) {
        super("Transação com o ID '%s' já existe.".formatted(id));
    }
}
