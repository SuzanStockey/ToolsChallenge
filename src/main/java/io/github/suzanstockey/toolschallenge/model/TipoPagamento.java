package io.github.suzanstockey.toolschallenge.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TipoPagamento {
    AVISTA("AVISTA"),
    PARCELADO_LOJA("PARCELADO LOJA"),
    PARCELADO_EMISSOR("PARCELADO EMISSOR");

    private final String valorJson;

    TipoPagamento(String valorJson) {
        this.valorJson = valorJson;
    }

    public static TipoPagamento fromValor(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Tipo de pagamento não pode ser nulo.");
        }

        return Arrays.stream(values())
                .filter(tipo -> tipo.valorJson.equalsIgnoreCase(valor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de pagamento inválido: %s".formatted(valor)));
    }
}
