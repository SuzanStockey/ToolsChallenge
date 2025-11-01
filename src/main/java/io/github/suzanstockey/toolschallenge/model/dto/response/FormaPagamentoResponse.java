package io.github.suzanstockey.toolschallenge.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FormaPagamentoResponse(

        @Schema(description = "Tipo de transação.", example = "AVISTA", allowableValues = {"AVISTA", "PARCELADO LOJA", "PARCELADO EMISSOR"})
        String tipo,

        @Schema(description = "Número de parcelas da transação", example = "1")
        String parcelas
){}
