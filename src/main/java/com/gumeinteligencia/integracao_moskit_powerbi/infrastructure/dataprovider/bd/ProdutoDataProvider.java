package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.ProdutoGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderBancoDadosException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.ProdutoMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.ProdutoRepository;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.ProdutoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProdutoDataProvider implements ProdutoGateway {

    private final ProdutoRepository repository;
    private final String MENSAGEM_ERRO_LISTAR = "Erro ao listar produtos.";
    private final String MENSAGEM_ERRO_SALVAR = "Erro ao salvar produto.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar produto pelo seu id.";

    @Override
    public List<Produto> listar() {
        List<ProdutoEntity> produtoEntities;

        try {
            produtoEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_LISTAR, ex.getCause());
        }

        return produtoEntities.stream().map(ProdutoMapper::paraDomain).toList();
    }

    @Override
    public Produto salvar(Produto novoProduto) {
        ProdutoEntity produtoSalvo;

        try {
            produtoSalvo = repository.save(ProdutoMapper.paraEntity(novoProduto));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_SALVAR, ex.getCause());
        }

        return ProdutoMapper.paraDomain(produtoSalvo);
    }

    @Override
    public Optional<Produto> consultarPorId(Integer id) {
        Optional<ProdutoEntity> produtoEntity;

        try {
            produtoEntity = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
        }

        return produtoEntity.map(ProdutoMapper::paraDomain);
    }
}
