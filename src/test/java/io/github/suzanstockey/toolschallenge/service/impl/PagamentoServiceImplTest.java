package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.FormaPagamento;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import io.github.suzanstockey.toolschallenge.service.strategy.PagamentoAvistaStrategy;
import io.github.suzanstockey.toolschallenge.service.strategy.PagamentoParceladoEmissorStrategy;
import io.github.suzanstockey.toolschallenge.service.strategy.PagamentoParceladoLojaStrategy;
import io.github.suzanstockey.toolschallenge.service.strategy.PagamentoStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceImplTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private TransacaoMapper mapper;

    @Mock
    private PagamentoAvistaStrategy avistaStrategy;

    @Mock
    private PagamentoParceladoLojaStrategy parceladoLojaStrategy;

    @Mock
    private PagamentoParceladoEmissorStrategy parceladoEmissorStrategy;

    private PagamentoServiceImpl pagamentoService;

    @BeforeEach
    void setUp() {
        when(avistaStrategy.getTipo()).thenReturn(TipoPagamento.AVISTA);
        when(parceladoLojaStrategy.getTipo()).thenReturn(TipoPagamento.PARCELADO_LOJA);
        when(parceladoEmissorStrategy.getTipo()).thenReturn(TipoPagamento.PARCELADO_EMISSOR);

        List<PagamentoStrategy> strategies = List.of(avistaStrategy, parceladoLojaStrategy, parceladoEmissorStrategy);

        pagamentoService = new PagamentoServiceImpl(strategies, repository, mapper);
    }

    @Test
    @DisplayName("Deve realizar um pagamento com sucesso (caminho feliz)")
    void deveRealizarPagamentoComSucesso() {

        var requestDTO = mock(PagamentoRequest.class);
        var transacaoEntidade = mock(Transacao.class);
        var formaPagamentoMock = mock(FormaPagamento.class);
        var responseDTO = mock(PagamentoResponse.class);

        when(mapper.toEntity(requestDTO)).thenReturn(transacaoEntidade);
        when(transacaoEntidade.getId()).thenReturn("12345-abc");
        when(repository.existsById("12345-abc")).thenReturn(false);

        when(transacaoEntidade.getFormaPagamento()).thenReturn(formaPagamentoMock);
        when(formaPagamentoMock.getTipo()).thenReturn(TipoPagamento.AVISTA);

        when(repository.save(transacaoEntidade)).thenReturn(transacaoEntidade);
        when(mapper.toResponse(transacaoEntidade)).thenReturn(responseDTO);

        PagamentoResponse resultado = pagamentoService.realizarPagamento(requestDTO);

        assertNotNull(resultado);
        assertEquals(responseDTO, resultado);

        verify(repository).existsById("12345-abc");
        verify(mapper).toEntity(requestDTO);

        verify(avistaStrategy, times(1)).autorizar(transacaoEntidade);

        verify(parceladoLojaStrategy, never()).autorizar(any());
        verify(parceladoEmissorStrategy, never()).autorizar(any());

        verify(repository).save(transacaoEntidade);
        verify(mapper).toResponse(transacaoEntidade);
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
    @DisplayName("Deve lançar UnsupportedOperationException para strategy não implementada")
    void deveLancarExcecaoParaStrategyNaoImplementada() {
        var requestDTO = mock(PagamentoRequest.class);
        var transacaoEntidade = mock(Transacao.class);
        var formaPagamentoMock = mock(FormaPagamento.class);

        when(mapper.toEntity(requestDTO)).thenReturn(transacaoEntidade);
        when(transacaoEntidade.getId()).thenReturn("12345-abc");
        when(repository.existsById("12345-abc")).thenReturn(false);

        when(transacaoEntidade.getFormaPagamento()).thenReturn(formaPagamentoMock);
        when(formaPagamentoMock.getTipo()).thenReturn(TipoPagamento.valueOf("AVISTA"));

        List<PagamentoStrategy> strategies = List.of(parceladoLojaStrategy, parceladoEmissorStrategy);
        PagamentoServiceImpl serviceSemAvista = new PagamentoServiceImpl(strategies, repository, mapper);

        assertThrows(UnsupportedOperationException.class, () -> serviceSemAvista.realizarPagamento(requestDTO));
    }

}