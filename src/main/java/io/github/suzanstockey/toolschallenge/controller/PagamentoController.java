package io.github.suzanstockey.toolschallenge.controller;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequestDTO;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponseDTO;
import io.github.suzanstockey.toolschallenge.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    /**
     * Endpoint para realizar um novo pagamento.
     * Recebe os dados da transação e retorna a transação processada.
     *
     * @param requestDTO O DTO contendo os dados da transação de pagamento.
     * @return ResponseEntity com o PagamentoResponseDTO e status HTTP.
     */
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(@Valid @RequestBody PagamentoRequestDTO requestDTO) {

        PagamentoResponseDTO responseDTO = pagamentoService.realizarPagamento(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint para consultar uma transação específica pelo ID.
     *
     * @param id O ID da transação a ser consultada.
     * @return ResponseEntity com o PagamentoResponseDTO da transação encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> consultarPorId(@PathVariable String id) {

        PagamentoResponseDTO responseDTO = pagamentoService.consultarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint para consultar todas as transações de pagamento.
     *
     * @return ResponseEntity com uma Lista de PagamentoResponseDTO.
     */
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> consultarTodos() {

        List<PagamentoResponseDTO> responseList = pagamentoService.consultarTodos();
        return ResponseEntity.ok(responseList);
    }

    /**
     * Endpoint para realizar o estorno (cancelamento) de um pagamento.
     *
     * @param id O ID da transação a ser estornada.
     * @return ResponseEntity com o PagamentoResponseDTO da transação estornada.
     */
    @PostMapping("/{id}/estorno")
    public ResponseEntity<PagamentoResponseDTO> realizarEstorno(@PathVariable String id) {

        PagamentoResponseDTO responseDTO = pagamentoService.realizarEstorno(id);
        return ResponseEntity.ok(responseDTO);
    }
}
