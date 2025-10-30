package io.github.suzanstockey.toolschallenge.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FormaPagamentoRequestDTO {

    @NotBlank(message="O campo 'tipo' é obrigatório.")
    private String tipo;

    @NotBlank(message="O campo 'parcelas' é obrigatório.")
    private String parcelas;
}
