package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.ProdutoNaoEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.ProdutoGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.ProdutoGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.ProdutoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.ProdutoMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.UsuarioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoUseCase {

    private final ProdutoGateway gateway;
    private final ProdutoGatewayApi gatewayApi;
    private final ProdutoMapper mapper;
    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    public int atualiza() {
        log.info("Começando as atualizações de produtos...");

        List<Produto> produtosNovos = gatewayApi.consultarProdutos()
                .stream()
                .map(this::buscaDadosNecessarios)
                .map(mapper::paraDomain)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        List<Produto> produtosAntigos = gateway.listar();

        List<Produto> produtosCadastrar = produtosNovos.stream()
                .filter(
                        produtoNovo ->
                                produtosAntigos.stream().noneMatch(produtoAntigo ->
                                        produtoAntigo.getName().equals(produtoNovo.getName())
                                )
                )
                .toList();

        produtosCadastrar.forEach(produto -> {
            Produto produtoSalvo = this.salvar(produto);
            contAtualizacoes.getAndIncrement();
            log.info("Produto atualizado com sucesso: {}", produtoSalvo);
        });

        log.info("Finalizado atualizações de produto. Produto: {}", produtosCadastrar);
        log.info("Quantidade de operações: {}", contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    public Produto salvar(Produto novoProduto) {
        log.info("Salvando novo produto. Produto: {}", novoProduto);

        Produto produtoSalvo = gateway.salvar(novoProduto);

        log.info("Produto salvo com sucesso ! Produto: {}", produtoSalvo);

        return produtoSalvo;
    }

    public Produto consultarPorId(Integer id) {
        log.info("Consultando produto pelo id. Id: {}", id);

        Optional<Produto> produtoOptional = gateway.consultarPorId(id);

        if(produtoOptional.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }

        Produto produto = produtoOptional.get();

        log.info("Finalizado consulta do produto. Produto: {}", produto);

        return produto;
    }

    private ProdutoDto buscaDadosNecessarios(ProdutoDto produto) {
        Usuario usuarioCriador = usuarioUseCase.consultarPorId(produto.getCreatedBy().id());
        produto.setCreatedBy(usuarioMapper.paraDto(usuarioCriador));
        return produto;
    }
}
