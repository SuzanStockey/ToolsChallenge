package io.github.suzanstockey.toolschallenge.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DescricaoRequest(

        @Schema(description="Valor da transação", example="500.50")
        @NotBlank(message="O campo 'valor' é obrigatório.")
        String valor,

        @JsonProperty("datahora")
        @Schema(description = "Data e hora da transação", example = "01/05/2021 18:30:00")
        @NotBlank(message="O campo 'datahora' é obrigatório.")
        String dataHora,

        @Schema(description = "Estabelecimento com o qual foi feito a transação", example = "PetShop Mundo Cão")
        @NotBlank(message="O campo 'estabelecimento' é obrigatório.")
        String estabelecimento
){}
