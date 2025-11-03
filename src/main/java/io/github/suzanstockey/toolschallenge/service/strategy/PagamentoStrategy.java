package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;

public interface PagamentoStrategy {

    /**
     * Aplica a lógica de autorização e processamento específica para esta estratégia de pagamento.
     * @param transacao A transação que está sendo processada.
     */
    void autorizar(Transacao transacao);


    /**
     * Retorna o TipoPagamento que esta estratégia manipula.
     * @return O enum TipoPagamento correspondente.
     */
    TipoPagamento getTipo();
}
