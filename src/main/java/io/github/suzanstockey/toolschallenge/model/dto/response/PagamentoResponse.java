package io.github.suzanstockey.toolschallenge.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PagamentoResponse(

    @JsonProperty("transacao")
    TransacaoResponse transacao
){}
