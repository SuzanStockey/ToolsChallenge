package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

        var requestDTO = mock(PagamentoRequest.class);

        var transacaoEntidade = mock(Transacao.class);
        var descricaoEntidade = mock(Descricao.class);
        when(transacaoEntidade.getId()).thenReturn("12345-abc");
        when(transacaoEntidade.getDescricao()).thenReturn(descricaoEntidade);

        var responseDTO = mock(PagamentoResponse.class);

        when(repository.existsById("12345-abc")).thenReturn(false);
        when(mapper.toEntity(requestDTO)).thenReturn(transacaoEntidade);
        when(repository.save(transacaoEntidade)).thenReturn(transacaoEntidade);
        when(mapper.toResponse(transacaoEntidade)).thenReturn(responseDTO);

        PagamentoResponse resultado = pagamentoService.realizarPagamento(requestDTO);

        assertNotNull(resultado);
        assertEquals(responseDTO, resultado);

        verify(repository).existsById("12345-abc");
        verify(repository).save(transacaoEntidade);
        verify(mapper).toEntity(requestDTO);
        verify(mapper).toResponse(transacaoEntidade);

        verify(descricaoEntidade).setStatus(StatusTransacao.AUTORIZADO);
    }


    @Test
    @DisplayName("Deve falhar ao tentar realizar pagamento com ID duplicado")
    void deveFalharPagamentoComIdDuplicado() {
        final String idDuplicado = "id-ja-existe-123";

        var requestDTO = mock(PagamentoRequest.class);
        var transacaoEntidade = mock(Transacao.class);
        when(mapper.toEntity(requestDTO)).thenReturn(transacaoEntidade);

        when(transacaoEntidade.getId()).thenReturn(idDuplicado);

        when(repository.existsById(idDuplicado)).thenReturn(true);

        TransacaoJaExistenteException exception = assertThrows(TransacaoJaExistenteException.class, () -> pagamentoService.realizarPagamento(requestDTO));

        assertTrue(exception.getMessage().contains(idDuplicado));
        verify(repository, never()).save(any(Transacao.class));
        verify(mapper, never()).toResponse(any(Transacao.class));
    }

    @Test
    @DisplayName("Deve consultar por ID com sucesso (caminho feliz)")
    void deveConsultarPorIdComSucesso() {
        final String idTeste = "id-existente-456";

        var transacaoEntidade = mock(Transacao.class);
        var response = mock(PagamentoResponse.class);

        when(repository.findById(idTeste)).thenReturn(Optional.of(transacaoEntidade));

        when(mapper.toResponse(transacaoEntidade)).thenReturn(response);

        PagamentoResponse resultado = pagamentoService.consultarPorId(idTeste);

        assertNotNull(resultado);

        verify(repository).findById(idTeste);
        verify(mapper).toResponse(transacaoEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao consultar por ID inexistente")
    void deveFalharConsultarPorIdInexistente(){
        final String idInexistente = "id-inexistente-789";

        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        TransacaoNaoEncontradaException exception = assertThrows(TransacaoNaoEncontradaException.class, () -> pagamentoService.consultarPorId(idInexistente));

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

        List<PagamentoResponse> resultados = pagamentoService.consultarTodos();

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

        List<PagamentoResponse> resultados = pagamentoService.consultarTodos();

        assertNotNull(resultados);
        assertEquals(0, resultados.size());

        verify(repository).findAll();
        verify(mapper, never()).toResponse(any(Transacao.class));
    }

    @Test
    @DisplayName("Deve realizar estorno com sucesso (caminho feliz)")
    void deveRealizarEstornoComSucesso(){
        final String idTeste = "id-autorizado-123";

        var transacaoEntidade = mock(Transacao.class);
        var descricaoMock = mock(Descricao.class);
        var response = mock(PagamentoResponse.class);

        when(transacaoEntidade.getDescricao()).thenReturn(descricaoMock);
        when(descricaoMock.getStatus()).thenReturn(StatusTransacao.AUTORIZADO);

        when(repository.findById(idTeste)).thenReturn(Optional.of(transacaoEntidade));
        when(repository.save(transacaoEntidade)).thenReturn(transacaoEntidade);
        when(mapper.toResponse(transacaoEntidade)).thenReturn(response);

        PagamentoResponse resultado = pagamentoService.realizarEstorno(idTeste);

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

        when(repository.findById(idTeste)).thenReturn(Optional.of(transacaoEntidade));

        EstornoNaoPermitidoException exception = assertThrows(EstornoNaoPermitidoException.class, () -> pagamentoService.realizarEstorno(idTeste));

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

        when(repository.findById(idTeste)).thenReturn(Optional.of(transacaoEntidade));

        EstornoNaoPermitidoException exception = assertThrows(EstornoNaoPermitidoException.class, () -> pagamentoService.realizarEstorno(idTeste));

        assertTrue(exception.getMessage().contains("foi negada"));

        verify(repository, never()).save(any(Transacao.class));
    }

}