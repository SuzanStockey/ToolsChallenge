package io.github.suzanstockey.toolschallenge.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TransacaoResponse(

        @Schema(description = "Número do cartão utilizado na transação", example = "4444123412341234")
        String cartao,

        @Schema(description = "Número identificador da transação", example = "100023568900001")
        String id,

        @Schema(description = "Detalhes da descrição da transação")
        DescricaoResponse descricao,

        @Schema(description = "Detalhes da forma de pagamento")
        FormaPagamentoResponse formaPagamento
){}
