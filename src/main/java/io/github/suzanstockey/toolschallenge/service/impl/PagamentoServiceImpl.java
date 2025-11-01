package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.exception.EstornoNaoPermitidoException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoJaExistenteException;
import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.StatusTransacao;
import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequest;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponse;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.PagamentoService;
import io.github.suzanstockey.toolschallenge.service.mapper.TransacaoMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final TransacaoRepository repository;
    private final TransacaoMapper mapper;

    @Override
    @Transactional
    public PagamentoResponse realizarPagamento(PagamentoRequest requestDTO) {
        Transacao transacao = mapper.toEntity(requestDTO);

        if (repository.existsById(transacao.getId())) {
            throw new TransacaoJaExistenteException(transacao.getId());
        }

        simularAutorizacao(transacao);

        Transacao transacaoSalva = repository.save(transacao);

        return mapper.toResponse(transacaoSalva);
    }

    /**
     * Simula o processo de autorização de uma transação.
     * Define o status como AUTORIZADO e gera os códigos de NSU e Autorização.
     * @param transacao A entidade Transacao a ser autorizada.
     */
    private void simularAutorizacao(Transacao transacao) {
        transacao.getDescricao().setStatus(StatusTransacao.AUTORIZADO);

        transacao.getDescricao().setNsu(gerarNsu());
        transacao.getDescricao().setCodigoAutorizacao(gerarCodigoAutorizacao());
    }

    /**
     * Gera um NSU (Número Sequencial Único) aleatório de 10 dígitos
     */
    private String gerarNsu() {
        long nsu = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        return String.format("%010d", nsu);
    }

    /**
     * Gera um Código de Autorização aleatório de 9 dígitos
     */
    private String gerarCodigoAutorizacao() {
        long codigo = ThreadLocalRandom.current().nextLong(100_000_000L, 1_000_000_000L);
        return String.format("%09d", codigo);
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
