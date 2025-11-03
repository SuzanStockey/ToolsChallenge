package io.github.suzanstockey.toolschallenge.controller;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.ApiErrorResponse;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.service.ConsultaPagamentoService;
import io.github.suzanstockey.toolschallenge.service.EstornoService;
import io.github.suzanstockey.toolschallenge.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
@Tag(name="API de Pagamentos", description = "Endpoints para criar, consultar e estornar transações")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final EstornoService estornoService;
    private final ConsultaPagamentoService consultaPagamentoService;

    /**
     * Endpoint para realizar um novo pagamento.
     * Recebe os dados da transação e retorna a transação processada.
     *
     * @param requestDTO O DTO contendo os dados da transação de pagamento.
     * @return ResponseEntity com o PagamentoResponseDTO e status HTTP.
     */
    @Operation(summary = "Realiza um novo pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento processado com sucesso",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PagamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: campos faltando)",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito: ID da transação já existe",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PagamentoResponse> realizarPagamento(@Valid @RequestBody PagamentoRequest requestDTO) {

        PagamentoResponse responseDTO = pagamentoService.realizarPagamento(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Endpoint para consultar uma transação específica pelo ID.
     *
     * @param id O ID da transação a ser consultada.
     * @return ResponseEntity com o PagamentoResponseDTO da transação encontrada.
     */
    @Operation(summary = "Consulta uma transação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PagamentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> consultarPorId(@Parameter(description = "ID da transação a ser consultada", example = "100023568900001") @PathVariable String id) {

        PagamentoResponse responseDTO = consultaPagamentoService.consultarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint para consultar todas as transações de pagamento.
     *
     * @return ResponseEntity com uma Lista de PagamentoResponseDTO.
     */
    @Operation(summary = "Consultar todas as transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transações (pode ser vazia)",
                         content = @Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PagamentoResponse.class))
                                            ))
    })
    @GetMapping
    public ResponseEntity<List<PagamentoResponse>> consultarTodos() {

        List<PagamentoResponse> responseList = consultaPagamentoService.consultarTodos();
        return ResponseEntity.ok(responseList);
    }

    /**
     * Endpoint para realizar o estorno (cancelamento) de um pagamento.
     *
     * @param id O ID da transação a ser estornada.
     * @return ResponseEntity com o PagamentoResponseDTO da transação estornada.
     */
    @Operation(summary = "Realiza o estorno de um pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estorno realizado com sucesso (status = CANCELADO)",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PagamentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada.",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Estorno não permitido (ex: já estornado ou negado)",
                         content = @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PostMapping("/{id}/estorno")
    public ResponseEntity<PagamentoResponse> realizarEstorno(@Parameter(description = "ID da transação a ser estornada", example = "100023568900001") @PathVariable String id) {

        PagamentoResponse responseDTO = estornoService.realizarEstorno(id);
        return ResponseEntity.ok(responseDTO);
    }
}
