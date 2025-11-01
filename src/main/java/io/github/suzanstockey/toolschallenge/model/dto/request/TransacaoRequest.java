package io.github.suzanstockey.toolschallenge.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequest(

        @Schema(description = "Número do cartão utilizado na transação", example = "4444123412341234")
        @NotBlank(message="O campo 'cartao' é obrigatório.")
        String cartao,

        @Schema(description = "Número identificador da transação", example = "100023568900001")
        @NotBlank(message="O campo 'id' (ID da transacao) é obrigatório.")
        String id,

        @Schema(description = "Detalhes da descrição da transação")
        @NotNull(message="O objeto 'descricao' não pode ser nulo.")
        @Valid
        DescricaoRequest descricao,

        @Schema(description = "Detalhes da forma de pagamento")
        @NotNull(message="O objeto 'formaPagamento' não pode ser nulo.")
        @Valid
        FormaPagamentoRequest formaPagamento
){}