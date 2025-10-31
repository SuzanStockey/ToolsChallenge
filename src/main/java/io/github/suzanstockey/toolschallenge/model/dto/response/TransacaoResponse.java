package io.github.suzanstockey.toolschallenge.model.dto.response;

public record TransacaoResponse(

    String cartao,
    String id,
    DescricaoResponse descricao,
    FormaPagamentoResponse formaPagamento
){}
