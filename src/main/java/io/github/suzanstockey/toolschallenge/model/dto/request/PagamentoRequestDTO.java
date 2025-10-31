package io.github.suzanstockey.toolschallenge.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequestDTO (

    @JsonProperty("transacao")
    @NotNull(message="O objeto 'transacao' não pode ser nulo.")
    @Valid
    TransacaoRequestDTO transacao
){}
