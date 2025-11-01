package io.github.suzanstockey.toolschallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.suzanstockey.toolschallenge.model.dto.request.DescricaoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.request.FormaPagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.request.TransacaoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar um pagamento com sucesso (HTTP 200)")
    void deveRealizarPagamentoComSucesso() throws Exception {

        var descReq = new DescricaoRequest("500.50", "01/05/2021 18:30:00", "PetShop Mundo Cão");
        var formaReq = new FormaPagamentoRequest("AVISTA", "1");
        var transReq = new TransacaoRequest("4444123412341234","id-unico-123",descReq, formaReq);
        var request = new PagamentoRequest(transReq);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transacao.id", is("id-unico-123")))
                .andExpect(jsonPath("$.transacao.descricao.status", is("AUTORIZADO")))
                .andExpect(jsonPath("$.transacao.descricao.nsu", notNullValue()))
                .andExpect(jsonPath("$.transacao.descricao.codigoAutorizacao", notNullValue()));

    }

    @Test
    @DisplayName("Deve falhar ao realizar pagamento com dados inválidos (HTTP 400)")
    void deveFalharPagamentoComDadosInvalidos() throws Exception {

        var descReqInvalida = new DescricaoRequest("500.50", "01/05/2021 18:30:00", null);
        var formaReq = new FormaPagamentoRequest("AVISTA", "1");
        var transReq = new TransacaoRequest("4444123412341234","id-invalido-456",descReqInvalida, formaReq);
        var request = new PagamentoRequest(transReq);

        String json = objectMapper.writeValueAsString(request);

        ResultActions result = mockMvc.perform(post("/api/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['transacao.descricao.estabelecimento']", containsString("O campo 'estabelecimento' é obrigatório.")));
    }
}