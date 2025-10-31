package io.github.suzanstockey.toolschallenge.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FormaPagamentoRequestDTO(

    @NotBlank(message="O campo 'tipo' é obrigatório.")
    String tipo,

    @NotBlank(message="O campo 'parcelas' é obrigatório.")
    String parcelas
) {}