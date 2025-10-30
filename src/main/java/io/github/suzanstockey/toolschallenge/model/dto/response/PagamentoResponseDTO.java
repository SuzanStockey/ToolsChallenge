package io.github.suzanstockey.toolschallenge.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PagamentoResponseDTO {

    @JsonProperty("transacao")
    private TransacaoResponseDTO transacao;
}
