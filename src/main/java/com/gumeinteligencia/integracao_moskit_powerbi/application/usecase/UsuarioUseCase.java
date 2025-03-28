package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumUsuarioEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.UsuarioNaoEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.UsuarioGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.UsuarioGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioUseCase {

    private final UsuarioGateway gateway;
    private final UsuarioGatewayApi gatewayApi;
    private final UsuarioMapper usuarioMapper;

    public Usuario consultarPorId(Integer id) {
        log.info("Consultando usuário pelo id. Id: {}", id);

        Optional<Usuario> usuarioOptional = gateway.consultarPorId(id);

        if(usuarioOptional.isEmpty()) {
            throw new UsuarioNaoEncontradoException(id);
        }

        Usuario usuario = usuarioOptional.get();

        log.info("Usuário consultado com sucesso. Usuário: {}", usuario);

        return usuario;
    }

    public int atualiza() {
        log.info("Começando atualizações de usuário...");

        List<Usuario> usuariosNovos = gatewayApi.consultarUsuarios()
                .stream()
                .map(usuarioMapper::paraDomain)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(usuariosNovos.isEmpty()) {
            throw new NenhumUsuarioEncontradoException();
        }

        List<Usuario> usuariosAntigos = gateway.listar();

        List<Usuario> usuariosCadastrar = usuariosNovos.stream()
                .filter(usuarioNovo ->
                        usuariosAntigos.stream().noneMatch(usuarioAntigo ->
                                usuarioAntigo.getName().equals(usuarioNovo.getName())
                        )
                )
                .toList();

        usuariosCadastrar.forEach(usuario -> {
            Usuario usuarioSalvo = this.salvar(usuario);
            contAtualizacoes.getAndIncrement();
            log.info("Usuario atualizado com sucesso: {}", usuarioSalvo);
        });

        log.info("Finalizado atualizações de usuário. Usuários: {}", usuariosCadastrar);
        log.info("Quantidade de operações: {}", contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    public Usuario salvar(Usuario usuarioNovo) {
        log.info("Cadastrando usuário. Usuário: {}", usuarioNovo);

        Usuario usuarioSalvo = gateway.salvar(usuarioNovo);

        log.info("Usuário cadastrado com sucesso. Usuário: {}", usuarioSalvo);

        return usuarioSalvo;
    }

}
