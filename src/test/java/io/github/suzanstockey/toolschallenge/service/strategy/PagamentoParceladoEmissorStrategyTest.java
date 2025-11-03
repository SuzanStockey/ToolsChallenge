package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PagamentoParceladoEmissorStrategyTest {

    private final PagamentoParceladoEmissorStrategy strategy = new PagamentoParceladoEmissorStrategy();

    @Test
    @DisplayName("Deve retornar o TipoPagamento PARCELADO_EMISSOR")
    void deveRetornarTipoCorreto() {
        assertEquals(TipoPagamento.PARCELADO_EMISSOR, strategy.getTipo());
    }
}