package io.github.suzanstockey.toolschallenge.repository;

import io.github.suzanstockey.toolschallenge.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {

}
