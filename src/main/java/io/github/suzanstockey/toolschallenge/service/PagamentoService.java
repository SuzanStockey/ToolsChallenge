package io.github.suzanstockey.toolschallenge.service;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;

import java.util.List;

/**
 * Interface para os serviços de lógica de negócio de Pagamento.
 */
public interface PagamentoService {

    /**
     * Processa uma nova transação de pagamento.
     *
     * @param requestDTO DTO com os dados do pagamento.
     * @return DTO com a resposta da transação.
     */
    PagamentoResponse realizarPagamento(PagamentoRequest requestDTO);

    /**
     * Consulta uma transação pelo seu ID.
     *
     * @param id O ID da transação.
     * @return DTO com a resposta da transação.
     */
    PagamentoResponse consultarPorId(String id);

    /**
     * Lista todas as transações de pagamento.
     *
     * @return Lista de DTOs de resposta.
     */
    List<PagamentoResponse> consultarTodos();

    /**
     * Realiza o estorno (cancelamento) de uma transação.
     *
     * @param id O ID da transação a ser estornada.
     * @return DTO com a resposta da transação estornada.
     */
    PagamentoResponse realizarEstorno(String id);
}
