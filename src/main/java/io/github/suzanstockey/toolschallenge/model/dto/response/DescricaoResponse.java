package io.github.suzanstockey.toolschallenge.model.dto.response;

import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.swagger.v3.oas.annotations.media.Schema;

public record DescricaoResponse(

        @Schema(description="Valor da transação", example="500.50")
        String valor,

        @Schema(description = "Data e hora da transação", example = "01/05/2021 18:30:00")
        String dataHora,

        @Schema(description = "Estabelecimento com o qual foi feito a transação", example = "PetShop Mundo Cão")
        String estabelecimento,

        @Schema(description = "NSU (Número Sequencial Único) da transação", example = "1234567890")
        String nsu,

        @Schema(description = "Código de Autorização da transação", example = "147258369")
        String codigoAutorizacao,

        @Schema(description = "Status da transação.", example = "AUTORIZADO")
        StatusTransacao status

){}
