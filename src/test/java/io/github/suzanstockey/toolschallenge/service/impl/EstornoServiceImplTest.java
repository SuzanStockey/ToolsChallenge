package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstornoServiceImplTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private TransacaoMapper mapper;

    @InjectMocks
    private EstornoServiceImpl service;

    @Test
    @DisplayName("Deve realizar estorno com sucesso (caminho feliz)")
    void deveRealizarEstornoComSucesso(){
        final String idTeste = "id-autorizado-123";

        var transacaoEntidade = mock(Transacao.class);
        var descricaoMock = mock(Descricao.class);
        var response = mock(PagamentoResponse.class);

        when(transacaoEntidade.getDescricao()).thenReturn(descricaoMock);
        when(descricaoMock.getStatus()).thenReturn(StatusTransacao.AUTORIZADO);

        when(repository.findByIdOrThrow(idTeste)).thenReturn(transacaoEntidade);
        when(repository.save(transacaoEntidade)).thenReturn(transacaoEntidade);
        when(mapper.toResponse(transacaoEntidade)).thenReturn(response);

        PagamentoResponse resultado = service.realizarEstorno(idTeste);

        assertNotNull(resultado);
        assertEquals(response,resultado);

        verify(descricaoMock).setStatus(StatusTransacao.CANCELADO);
        verify(repository).save(transacaoEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao tentar estornar transação já CANCELADA")
    void deveFalharEstornoJaCancelado() {
        final String idTeste = "id-cancelado-456";

        var transacaoEntidade = mock(Transacao.class);
        var descricaoMock = mock(Descricao.class);

        when(transacaoEntidade.getDescricao()).thenReturn(descricaoMock);
        when(descricaoMock.getStatus()).thenReturn(StatusTransacao.CANCELADO);

        when(repository.findByIdOrThrow(idTeste)).thenReturn(transacaoEntidade);

        EstornoNaoPermitidoException exception = assertThrows(EstornoNaoPermitidoException.class, () -> service.realizarEstorno(idTeste));

        assertTrue(exception.getMessage().contains("já está estornada"));

        verify(repository, never()).save(any(Transacao.class));
    }

    @Test
    @DisplayName("Deve falhar ao tentar estornar transação NEGADA")
    void deveFalharEstornoNegado(){
        final String idTeste = "id-negado-789";

        var transacaoEntidade = mock(Transacao.class);
        var descricaoMock = mock(Descricao.class);

        when(transacaoEntidade.getDescricao()).thenReturn(descricaoMock);
        when(descricaoMock.getStatus()).thenReturn(StatusTransacao.NEGADO);

        when(repository.findByIdOrThrow(idTeste)).thenReturn(transacaoEntidade);

        EstornoNaoPermitidoException exception = assertThrows(EstornoNaoPermitidoException.class, () -> service.realizarEstorno(idTeste));

        assertTrue(exception.getMessage().contains("foi negada"));

        verify(repository, never()).save(any(Transacao.class));
    }
}
