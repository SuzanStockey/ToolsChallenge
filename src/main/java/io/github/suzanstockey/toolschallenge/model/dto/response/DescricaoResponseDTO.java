package io.github.suzanstockey.toolschallenge.model.dto.response;

import lombok.Data;

@Data
public class DescricaoResponseDTO {

    private String valor;
    private String dataHora;
    private String estabelecimento;
    private String nsu;
    private String codigoAutorizacao;
    private String status;
}
