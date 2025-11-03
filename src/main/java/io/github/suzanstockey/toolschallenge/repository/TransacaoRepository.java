package io.github.suzanstockey.toolschallenge.repository;

import io.github.suzanstockey.toolschallenge.exception.TransacaoNaoEncontradaException;
import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {

    /**
     * Busca uma transação pelo ID ou lança uma exceção
     * TransacaoNaoEncontradaException se não for encontrada.
     *
     * @param id O ID da transação.
     * @return A Entidade Transacao encontrada.
     */
    default Transacao findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException(id));
    }

}
