package io.github.suzanstockey.toolschallenge.service;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;

/**
 * Interface de serviço focada apenas na operação de CRIAR um novo pagamento.
 */
public interface PagamentoService {

    /**
     * Processa uma nova transação de pagamento.
     *
     * @param requestDTO DTO com os dados do pagamento.
     * @return DTO com a resposta da transação.
     * @throws io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException se o ID já existir.
     * @throws UnsupportedOperationException se não houver strategy para o TipoPagamento.
     */
    PagamentoResponse realizarPagamento(PagamentoRequest requestDTO);
}
