package io.github.suzanstockey.toolschallenge.service.strategy;

import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe abstrata que implementa a lógica de autorização padrão, compartilhada por todas as estratégias de pagamento atuais.
 */
public abstract class AbstractPagamentoStrategy implements PagamentoStrategy{

    /**
     * Implementação padrão da lógica de autorização.
     * Define o status como AUTORIZADO e gera o NSU e o Código de Autorização.
     */
    @Override
    public void autorizar(Transacao transacao) {
        transacao.getDescricao().setStatus(StatusTransacao.AUTORIZADO);
        transacao.getDescricao().setNsu(gerarNsu());
        transacao.getDescricao().setCodigoAutorizacao(gerarCodigoAutorizacao());
    }

    /**
    * Gera um NSU (Número Sequencial Único) aleatório de 10 dígitos.
    */
    protected String gerarNsu(){
        return String.format("%010d", ThreadLocalRandom.current().nextLong(100_000_000L, 1_000_000_000L));
    }

    /**
     * Gera um Código de Autorização aleatório de 9 dígitos.
     */
    protected String gerarCodigoAutorizacao() {
        return String.format("%09d", ThreadLocalRandom.current().nextInt(100_000_000, 1_000_000_000));
    }
}
