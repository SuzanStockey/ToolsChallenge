package io.github.suzanstockey.toolschallenge.controller;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequestDTO;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    //TODO: Injetar PagamentoService quando ele for criado.

    /**
     * Endpoint para realizar um novo pagamento.
     * Recebe os dados da transação e retorna a transação processada.
     *
     * @param requestDTO O DTO contendo os dados da transação de pagamento.
     * @return ResponseEntity com o PagamentoResponseDTO e status HTTP.
     */
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(@Valid @RequestBody PagamentoRequestDTO requestDTO) {

        // A lógica de negócio será implementada no PagamentoService
        return ResponseEntity.ok(null);
    }

    /**
     * Endpoint para consultar uma transação específica pelo ID.
     *
     * @param id O ID da transação a ser consultada.
     * @return ResponseEntity com o PagamentoResponseDTO da transação encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> consultarPorId(@PathVariable String id) {

        // A lógica de negócio será implementada no PagamentoService
        return ResponseEntity.ok(null);
    }

    /**
     * Endpoint para consultar todas as transações de pagamento.
     *
     * @return ResponseEntity com uma Lista de PagamentoResponseDTO.
     */
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> consultarTodos() {

        // A lógica de negócio será implementada no PagamentoService
        return ResponseEntity.ok(null);
    }

    /**
     * Endpoint para realizar o estorno (cancelamento) de um pagamento.
     *
     * @param id O ID da transação a ser estornada.
     * @return ResponseEntity com o PagamentoResponseDTO da transação estornada.
     */
    @PostMapping("/{id}/estorno")
    public ResponseEntity<PagamentoResponseDTO> realizarEstorno(@PathVariable String id) {

        // A lógica de negócio será implementada no PagamentoService
        return ResponseEntity.ok(null);
    }
}
