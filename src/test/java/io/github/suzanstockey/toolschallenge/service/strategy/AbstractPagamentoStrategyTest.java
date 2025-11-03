package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import io.github.suzanstockey.toolschallenge.model.entity.Descricao;
import io.github.suzanstockey.toolschallenge.model.entity.FormaPagamento;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AbstractPagamentoStrategyTest {

    private static class ConcreteTestStrategy extends AbstractPagamentoStrategy {
        @Override
        public TipoPagamento getTipo() {
            return TipoPagamento.AVISTA;
        }
    }

    private AbstractPagamentoStrategy defaultStrategy;

    @BeforeEach
    void setUp() {
        defaultStrategy = new ConcreteTestStrategy();
    }

    @Test
    @DisplayName("Deve autorizar transação com a lógica default (Status, NSU, CodAutorizacao)")
    void deveAutorizarTransacaoCorretamente() {
        Transacao transacao = new Transacao();
        transacao.setDescricao(new Descricao());
        transacao.setFormaPagamento(new FormaPagamento());

        defaultStrategy.autorizar(transacao);

        Descricao descricao = transacao.getDescricao();

        assertEquals(StatusTransacao.AUTORIZADO, descricao.getStatus());
        assertNotNull(descricao.getCodigoAutorizacao());
        assertEquals(9, descricao.getCodigoAutorizacao().length());
        assertNotNull(descricao.getNsu());
        assertEquals(10, descricao.getNsu().length());
    }
}