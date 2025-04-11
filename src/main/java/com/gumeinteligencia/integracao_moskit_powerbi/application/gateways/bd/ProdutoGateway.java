package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {
    List<Produto> listar();

    Produto salvar(Produto novoProduto);

    Optional<Produto> consultarPorId(Integer id);
}
