package io.github.suzanstockey.toolschallenge.exception;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(String id) {
        super("Transação com o ID '%s' não foi encontrada.".formatted(id));
    }
}
