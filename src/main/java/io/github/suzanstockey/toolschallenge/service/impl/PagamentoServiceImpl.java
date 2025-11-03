package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
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
import java.util.stream.Collectors;

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


    @Override
    @Transactional(readOnly = true)
    public PagamentoResponse consultarPorId(String id) {
        Transacao transacao = findTransacaoById(id);
        return mapper.toResponse(transacao);
    }

    @Override
    public List<PagamentoResponse> consultarTodos() {
        List<Transacao> transacoes = repository.findAll();

        return transacoes.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagamentoResponse realizarEstorno(String id) {
        Transacao transacao = findTransacaoById(id);

        StatusTransacao statusAtual = transacao.getDescricao().getStatus();

        switch (statusAtual) {
            case CANCELADO -> throw new EstornoNaoPermitidoException("A transação já está estornada (CANCELADO).");
            case NEGADO -> throw new EstornoNaoPermitidoException("Não é possível estornar uma transação que foi negada.");
            case AUTORIZADO -> transacao.getDescricao().setStatus(StatusTransacao.CANCELADO);
        }

        Transacao transacaoEstornada = repository.save(transacao);

        return mapper.toResponse(transacaoEstornada);
    }

    /**
     * Busca uma Transacao pelo ID no repositório.
     * Lança TransacaoNaoEncontradaException se não existir.
     *
     * @param id O ID da transação.
     * @return A Entidade Transacao encontrada.
     */
    private Transacao findTransacaoById(String id) {
        return repository.findById(id).orElseThrow(() -> new TransacaoNaoEncontradaException(id));
    }
}
