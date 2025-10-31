package io.github.suzanstockey.toolschallenge.service.impl;

import io.github.suzanstockey.toolschallenge.model.dto.request.PagamentoRequestDTO;
import io.github.suzanstockey.toolschallenge.model.dto.response.PagamentoResponseDTO;
import io.github.suzanstockey.toolschallenge.repository.TransacaoRepository;
import io.github.suzanstockey.toolschallenge.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final TransacaoRepository repository;

    @Override
    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO requestDTO) {
        //TODO: Implementar lógica de autorização e salvamento
        throw new UnsupportedOperationException("Método realizarPagamento ainda não implementado");
    }

    public PagamentoResponseDTO consultarPorId(String id) {
        //TODO: Implementar lógica de buscar por ID
        throw new UnsupportedOperationException("Método consultarPorId ainda não implementado");
    }

    @Override
    public List<PagamentoResponseDTO> consultarTodos() {
        //TODO: Implementar lógica de busca de todos
        throw new UnsupportedOperationException("Método consultarTodos ainda não implementado");
    }

    @Override
    public PagamentoResponseDTO realizarEstorno(String id) {
        //TODO: Implementar lógica de estorno (atualização)
        throw new UnsupportedOperationException("Método realizarEstorno ainda não implementado");
    }
}
