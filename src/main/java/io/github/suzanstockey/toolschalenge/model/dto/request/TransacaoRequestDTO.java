package io.github.suzanstockey.toolschalenge.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransacaoRequestDTO {

    @NotBlank(message="O campo 'cartao' é obrigatório.")
    private String cartao;

    @NotBlank(message="O campo 'id' (ID da transacao) é obrigatório.")
    private String id;

    @NotNull(message="O objeto 'descricao' não pode ser nulo.")
    @Valid
    private DescricaoRequestDTO descricao;

    @NotNull(message="O objeto 'formaPagamento' não pode ser nulo.")
    @Valid
    private FormaPagamentoRequestDTO formaPagamento;
}
