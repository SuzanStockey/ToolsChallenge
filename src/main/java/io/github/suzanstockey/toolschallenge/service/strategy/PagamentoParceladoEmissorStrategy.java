package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoParceladoEmissorStrategy extends AbstractPagamentoStrategy {

    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.PARCELADO_EMISSOR;
    }
}
