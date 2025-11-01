package io.github.suzanstockey.toolschallenge.model.entity;

import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
@Data
public class Descricao {

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String estabelecimento;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    private String nsu;

    private String codigoAutorizacao;
}
