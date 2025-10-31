package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
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
}