package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoAvistaStrategy extends AbstractPagamentoStrategy {

    @Override
    public TipoPagamento getTipo() {
        return TipoPagamento.AVISTA;
    }
}
