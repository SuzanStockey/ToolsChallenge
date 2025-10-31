package io.github.suzanstockey.toolschallenge.model.dto.response;

public record TransacaoResponseDTO (

    String cartao,
    String id,
    DescricaoResponseDTO descricao,
    FormaPagamentoResponseDTO formaPagamento
){}
