package io.github.suzanstockey.toolschallenge.model.dto.response;

public record DescricaoResponseDTO (

    String valor,
    String dataHora,
    String estabelecimento,
    String nsu,
    String codigoAutorizacao,
    String status

){}
