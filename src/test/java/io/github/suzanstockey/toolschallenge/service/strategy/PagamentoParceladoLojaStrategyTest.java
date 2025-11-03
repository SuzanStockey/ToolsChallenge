package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PagamentoParceladoLojaStrategyTest {

    private final PagamentoParceladoLojaStrategy strategy = new PagamentoParceladoLojaStrategy();

    @Test
    void deveRetornarTipoCorreto() {
        assertEquals(TipoPagamento.PARCELADO_LOJA, strategy.getTipo());
    }
}