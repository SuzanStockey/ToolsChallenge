package io.github.suzanstockey.toolschallenge.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DescricaoRequestDTO {

    @NotBlank(message="O campo 'valor' é obrigatório.")
    private String valor;

    @JsonProperty("datahora")
    @NotBlank(message="O campo 'datahora' é obrigatório.")
    private String dataHora;

    @NotBlank(message="O campo 'estabelecimento' é obrigatório.")
    private String estabelecimento;
}
