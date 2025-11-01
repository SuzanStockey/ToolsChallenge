package io.github.suzanstockey.toolschallenge.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FormaPagamentoResponse(

        @Schema(description = "Tipo de transação. Deve ser \"AVISTA\", \"PARCELADO LOJA\" ou \"PARCELADO EMISSOR\"", example = "AVISTA")
        String tipo,

        @Schema(description = "Número de parcelas da transação", example = "1")
        String parcelas
){}
