package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import io.github.suzanstockey.toolschallenge.model.entity.Descricao;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PagamentoAvistaStrategyTest {

    private final PagamentoAvistaStrategy strategy = new PagamentoAvistaStrategy();

    @Test
    void deveRetornarTipoCorreto() {
        assertEquals(TipoPagamento.AVISTA, strategy.getTipo());
    }

    @Test
    void deveAutorizarTransacaoCorretamente() {
        Transacao transacao = new Transacao();
        transacao.setDescricao(new Descricao());

        strategy.autorizar(transacao);

        Descricao descricao = transacao.getDescricao();

        assertEquals(StatusTransacao.AUTORIZADO, descricao.getStatus());
        assertNotNull(descricao.getCodigoAutorizacao());
        assertEquals(9, descricao.getCodigoAutorizacao().length());
        assertNotNull(descricao.getNsu());
        assertEquals(10, descricao.getNsu().length());
    }
}