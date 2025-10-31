package io.github.suzanstockey.toolschallenge.service;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequestDTO;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponseDTO;

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
    PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO requestDTO);

    /**
     * Consulta uma transação pelo seu ID.
     *
     * @param id O ID da transação.
     * @return DTO com a resposta da transação.
     */
    PagamentoResponseDTO consultarPorId(String id);

    /**
     * Lista todas as transações de pagamento.
     *
     * @return Lista de DTOs de resposta.
     */
    List<PagamentoResponseDTO> consultarTodos();

    /**
     * Realiza o estorno (cancelamento) de uma transação.
     *
     * @param id O ID da transação a ser estornada.
     * @return DTO com a resposta da transação estornada.
     */
    PagamentoResponseDTO realizarEstorno(String id);
}
