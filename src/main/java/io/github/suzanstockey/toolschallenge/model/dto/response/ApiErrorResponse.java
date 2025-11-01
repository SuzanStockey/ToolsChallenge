package io.github.suzanstockey.toolschallenge.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de erro padronizada da API")
public record ApiErrorResponse(

        @Schema(description = "Mensagem de erro legível", example = "Transação não encontrada")
        String mensagem
){}
