package io.github.suzanstockey.toolschallenge.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record FormaPagamentoRequest(

        @Schema(description = "Tipo de pagamento.",
                example = "AVISTA",
                allowableValues = {"AVISTA", "PARCELADO LOJA", "PARCELADO EMISSOR"})
        @NotBlank(message="O campo 'tipo' é obrigatório.")
        String tipo,

        @Schema(description = "Número de parcelas da transação (deve ser '1' para AVISTA)", example = "1")
        @NotBlank(message="O campo 'parcelas' é obrigatório.")
        String parcelas
) {}