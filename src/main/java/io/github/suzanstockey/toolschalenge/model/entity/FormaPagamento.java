package io.github.suzanstockey.toolschalenge.model.entity;

import io.github.suzanstockey.toolschalenge.model.TipoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
public class FormaPagamento {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;

    @Column(nullable = false)
    private Integer parcelas;
}
