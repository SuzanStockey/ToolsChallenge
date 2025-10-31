package io.github.suzanstockey.toolschallenge.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PagamentoResponseDTO (

    @JsonProperty("transacao")
    TransacaoResponseDTO transacao
){}
