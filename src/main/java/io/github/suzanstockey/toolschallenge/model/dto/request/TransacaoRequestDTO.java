package io.github.suzanstockey.toolschallenge.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDTO(

    @NotBlank(message="O campo 'cartao' é obrigatório.")
    String cartao,

    @NotBlank(message="O campo 'id' (ID da transacao) é obrigatório.")
    String id,

    @NotNull(message="O objeto 'descricao' não pode ser nulo.")
    @Valid
    DescricaoRequestDTO descricao,

    @NotNull(message="O objeto 'formaPagamento' não pode ser nulo.")
    @Valid
    FormaPagamentoRequestDTO formaPagamento
){}