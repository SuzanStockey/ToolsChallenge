package io.github.suzanstockey.toolschallenge.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record DescricaoRequestDTO (

    @NotBlank(message="O campo 'valor' é obrigatório.")
    String valor,

    @JsonProperty("datahora")
    @NotBlank(message="O campo 'datahora' é obrigatório.")
    String dataHora,

    @NotBlank(message="O campo 'estabelecimento' é obrigatório.")
    String estabelecimento
){}
