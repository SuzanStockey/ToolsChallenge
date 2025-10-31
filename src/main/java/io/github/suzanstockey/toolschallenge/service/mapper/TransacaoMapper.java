package io.github.suzanstockey.toolschallenge.service.mapper;

import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.DescricaoResponse;
import io.github.suzanstockey.toolschallenge.model.dto.response.FormaPagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.dto.response.TransacaoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.Descricao;
import io.github.suzanstockey.toolschallenge.model.entity.FormaPagamento;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransacaoMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Converte o DTO de Request para a Entidade Transacao.
     */

    public Transacao toEntity(PagamentoRequest requestDTO) {
        var dto = requestDTO.transacao();

        Descricao descricao = new Descricao();
        descricao.setValor(new BigDecimal(dto.descricao().valor()));
        descricao.setDataHora(parseDataHora(dto.descricao().dataHora()));
        descricao.setEstabelecimento(dto.descricao().estabelecimento());

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo(parseTipoPagamento(dto.formaPagamento().tipo()));
        formaPagamento.setParcelas(Integer.parseInt(dto.formaPagamento().parcelas()));

        Transacao transacao = new Transacao();
        transacao.setCartao(dto.cartao());
        transacao.setDescricao(descricao);
        transacao.setFormaPagamento(formaPagamento);

        return transacao;
    }

    /**
     * Converte a Entidade Transacao para o DTO (Record) de Response.
     */
    public PagamentoResponse toResponse(Transacao entity) {
        var descEntity = entity.getDescricao();
        var formaEntity = entity.getFormaPagamento();

        DescricaoResponse descResp = new DescricaoResponse(
                descEntity.getValor().toString(),
                formatDataHora(descEntity.getDataHora()),
                descEntity.getEstabelecimento(),
                descEntity.getNsu(),
                descEntity.getCodigoAutorizacao(),
                descEntity.getStatus().toString()
        );

        FormaPagamentoResponse formaResp = new FormaPagamentoResponse(
                formaEntity.getTipo().toString(),
                formaEntity.getParcelas().toString()
        );

        TransacaoResponse transResp = new TransacaoResponse(
                entity.getCartao(),
                entity.getId(),
                descResp,
                formaResp
        );

        return new PagamentoResponse(transResp);
    }

    /**
     * Converte a String de tipo de pagamento no Enum TipoPagamento
     */
    private TipoPagamento parseTipoPagamento(String tipo) {
        return TipoPagamento.valueOf(tipo.replace(" ", "_").toUpperCase());
    }

    /**
     * Converte a String de data e hora no LocalDateTime
     */
    private LocalDateTime parseDataHora(String dataHora) {
        return LocalDateTime.parse(dataHora, FORMATTER);
    }

    /**
     * Converte o objeto LocalDateTime em uma String formatada
     */
    private String formatDataHora(LocalDateTime dataHora) {
        return dataHora.format(FORMATTER);
    }
}
