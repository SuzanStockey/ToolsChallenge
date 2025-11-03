package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.ConsultaPagamentoService;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultaPagamentoServiceImpl implements ConsultaPagamentoService {

    private final TransacaoRepository repository;
    private final TransacaoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PagamentoResponse consultarPorId(String id) {
        Transacao transacao = repository.findByIdOrThrow(id);
        return mapper.toResponse(transacao);
    }

    @Override
    public List<PagamentoResponse> consultarTodos() {
        List<Transacao> transacoes = repository.findAll();

        return transacoes.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
