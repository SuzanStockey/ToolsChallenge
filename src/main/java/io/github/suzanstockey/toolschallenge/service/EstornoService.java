package io.github.suzanstockey.toolschallenge.service;

import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;

public interface EstornoService {

    /**
     * Realiza o estorno (cancelamento) de uma transação.
     *
     * @param id O ID da transação a ser estornada.
     * @return DTO com a resposta da transação estornada.
     * @throws io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException se o ID não existir.
     * @throws io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException se a transação já estiver CANCELADA ou NEGADA.
     */
    PagamentoResponse realizarEstorno(String id);
}
