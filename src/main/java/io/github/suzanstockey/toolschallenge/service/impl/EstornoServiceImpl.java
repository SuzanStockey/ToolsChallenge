package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.EstornoService;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstornoServiceImpl implements EstornoService {

    private final TransacaoRepository repository;
    private final TransacaoMapper mapper;

    @Override
    @Transactional
    public PagamentoResponse realizarEstorno(String id) {
        Transacao transacao = repository.findByIdOrThrow(id);

        StatusTransacao statusAtual = transacao.getDescricao().getStatus();

        switch (statusAtual) {
            case CANCELADO -> throw new EstornoNaoPermitidoException("A transação já está estornada (CANCELADO).");
            case NEGADO -> throw new EstornoNaoPermitidoException("Não é possível estornar uma transação que foi negada.");
            case AUTORIZADO -> transacao.getDescricao().setStatus(StatusTransacao.CANCELADO);
        }

        Transacao transacaoEstornada = repository.save(transacao);

        return mapper.toResponse(transacaoEstornada);
    }
}
