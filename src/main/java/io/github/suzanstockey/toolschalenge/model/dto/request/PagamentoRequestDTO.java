package io.github.suzanstockey.toolschalenge.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagamentoRequestDTO {

    @JsonProperty("transacao")
    @NotNull(message="O objeto 'transacao' não pode ser nulo.")
    @Valid
    private TransacaoRequestDTO transacao;
}
