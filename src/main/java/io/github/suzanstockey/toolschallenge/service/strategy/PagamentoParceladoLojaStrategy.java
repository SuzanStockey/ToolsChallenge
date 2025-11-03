package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;

public class PagamentoParceladoLojaStrategy extends AbstractPagamentoStrategy {

    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.PARCELADO_LOJA;
    }
}
