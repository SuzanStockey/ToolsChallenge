package io.github.suzanstockey.toolschalenge.model.dto.response;

import lombok.Data;

@Data
public class TransacaoResponseDTO {

    private String cartao;
    private String id;
    private DescricaoResponseDTO descricao;
    private FormaPagamentoResponseDTO formaPagamento;
}
