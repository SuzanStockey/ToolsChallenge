package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequestDTO;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponseDTO;
import io.github.suzanstockey.toolschallenge.model.entity.Descricao;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceImplTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private TransacaoMapper mapper;

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    @Test
    @DisplayName("Deve realizar um pagamento com sucesso (caminho feliz)")
    void deveRealizarPagamentoComSucesso() {

        var requestDTO = mock(PagamentoRequestDTO.class);

        var transacaoEntidade = mock(Transacao.class);
        var descricaoEntidade = mock(Descricao.class);
        when(transacaoEntidade.getId()).thenReturn("12345-abc");
        when(transacaoEntidade.getDescricao()).thenReturn(descricaoEntidade);

        var responseDTO = mock(PagamentoResponseDTO.class);

        when(repository.existsById("12345-abc")).thenReturn(false);
        when(mapper.toEntity(requestDTO)).thenReturn(transacaoEntidade);
        when(repository.save(transacaoEntidade)).thenReturn(transacaoEntidade);
        when(mapper.toResponse(transacaoEntidade)).thenReturn(responseDTO);

        PagamentoResponseDTO resultado = pagamentoService.realizarPagamento(requestDTO);

        assertNotNull(resultado);
        assertEquals(responseDTO, resultado);

        verify(repository).existsById("12345-abc");
        verify(repository).save(transacaoEntidade);
        verify(mapper).toEntity(requestDTO);
        verify(mapper).toResponse(transacaoEntidade);

        verify(descricaoEntidade).setStatus(StatusTransacao.AUTORIZADO);
    }
}