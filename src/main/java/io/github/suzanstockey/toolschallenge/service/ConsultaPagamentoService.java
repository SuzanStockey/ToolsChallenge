package io.github.suzanstockey.toolschallenge.service;

import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;

import java.util.List;

public interface ConsultaPagamentoService {

    /**
     * Busca uma transação pelo seu ID.
     *
     * @param id O ID da transação.
     * @return O DTO de resposta do pagamento.
     * @throws TransacaoNaoEncontradaException se o ID não existir.
     */
    PagamentoResponse consultarPorId(String id);

    /**
     * Lista todas as transações de pagamento existentes.
     *
     * @return Lista de DTOs de resposta de pagamento.
     */
    List<PagamentoResponse> consultarTodos();
}
