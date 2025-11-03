package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaPagamentoServiceImplTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private TransacaoMapper mapper;

    @InjectMocks
    private ConsultaPagamentoServiceImpl service;

    @Test
    @DisplayName("Deve consultar por ID com sucesso (caminho feliz)")
    void deveConsultarPorIdComSucesso() {
        final String idTeste = "id-existente-456";

        var transacaoEntidade = mock(Transacao.class);
        var response = mock(PagamentoResponse.class);

        when(repository.findByIdOrThrow(idTeste)).thenReturn(transacaoEntidade);

        when(mapper.toResponse(transacaoEntidade)).thenReturn(response);

        PagamentoResponse resultado = service.consultarPorId(idTeste);

        assertNotNull(resultado);

        verify(repository).findByIdOrThrow(idTeste);
        verify(mapper).toResponse(transacaoEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao consultar por ID inexistente")
    void deveFalharConsultarPorIdInexistente(){
        final String idInexistente = "id-inexistente-789";

        when(repository.findByIdOrThrow(idInexistente)).thenThrow(new TransacaoNaoEncontradaException(idInexistente));

        TransacaoNaoEncontradaException exception = assertThrows(TransacaoNaoEncontradaException.class, () -> service.consultarPorId(idInexistente));

        assertTrue(exception.getMessage().contains(idInexistente));

        verify(mapper, never()).toResponse(any(Transacao.class));
    }

    @Test
    @DisplayName("Deve consultar todos os pagamentos com sucesso (caminho feliz)")
    void deveConsultarTodosComSucesso() {
        var transacao1 = mock(Transacao.class);
        var transacao2 = mock(Transacao.class);
        var response1 = mock(PagamentoResponse.class);
        var response2 = mock(PagamentoResponse.class);

        List<Transacao> transacoesEntidades = List.of(transacao1, transacao2);

        when(repository.findAll()).thenReturn(transacoesEntidades);

        when(mapper.toResponse(transacao1)).thenReturn(response1);
        when(mapper.toResponse(transacao2)).thenReturn(response2);

        List<PagamentoResponse> resultados = service.consultarTodos();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertTrue(resultados.contains(response1));
        assertTrue(resultados.contains(response2));

        verify(repository).findAll();
        verify(mapper, times(2)).toResponse(any(Transacao.class));
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia ao consultar todos e não houver transações")
    void deveRetornarListaVaziaAoConsultarTodos(){

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<PagamentoResponse> resultados = service.consultarTodos();

        assertNotNull(resultados);
        assertEquals(0, resultados.size());

        verify(repository).findAll();
        verify(mapper, never()).toResponse(any(Transacao.class));
    }

}
