package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.model.TipoPagamento;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.PagamentoService;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import io.github.suzanstockey.toolschallenge.service.strategy.PagamentoStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    private final Map<TipoPagamento, PagamentoStrategy> strategies = new EnumMap<>(TipoPagamento.class);
    private final TransacaoRepository repository;
    private final TransacaoMapper mapper;

    public PagamentoServiceImpl(List<PagamentoStrategy> strategyList, TransacaoRepository repository, TransacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        strategyList.forEach(strategy -> strategies.put(strategy.getTipo(), strategy));
    }

    @Override
    @Transactional
    public PagamentoResponse realizarPagamento(PagamentoRequest requestDTO) {
        Transacao transacao = mapper.toEntity(requestDTO);

        if (repository.existsById(transacao.getId())) {
            throw new TransacaoJaExistenteException(transacao.getId());
        }

        TipoPagamento tipoPagamento = transacao.getFormaPagamento().getTipo();
        PagamentoStrategy strategy = strategies.get(tipoPagamento);

        if (strategy == null) {
            throw new UnsupportedOperationException("Tipo de pagamento não suportado: %s".formatted(tipoPagamento));
        }

        strategy.autorizar(transacao);

        Transacao transacaoSalva = repository.save(transacao);
        return mapper.toResponse(transacaoSalva);
    }





}
