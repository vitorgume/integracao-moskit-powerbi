package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd.UsuarioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaUsuarioService implements Atualiza<UsuarioDto>{

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final UsuarioDataProvider dataProvider;

    public AtualizaUsuarioService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            UsuarioDataProvider dataProvider
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
    }

    @Override
    public int atualiza() {
        System.out.println("Começando atualizações de usuário...");
        List<Usuario> usuariosNovos = consultaApi().stream().map(UsuarioMapper::paraDomainDeDto).toList();
        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(usuariosNovos.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado");
        }

        List<Usuario> usuariosAntigos = dataProvider.listarUsuarios().stream().map(UsuarioMapper::paraDomain).toList();

        List<Usuario> usuariosCadastrar = usuariosNovos.stream()
                .filter(usuarioNovo ->
                        usuariosAntigos.stream().noneMatch(usuarioAntigo ->
                                usuarioAntigo.getName().equals(usuarioNovo.getName())
                        )
                )
                .toList();

        usuariosCadastrar.forEach(usuario -> {
            Usuario usuarioSalvo = UsuarioMapper.paraDomain(dataProvider.salvar(UsuarioMapper.paraEntity(usuario)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Usuario salvo com sucesso: " + usuarioSalvo);
        });

        System.out.println("Finalizado atualizações de usuário...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    @Override
    public List<UsuarioDto> consultaApi() {
        System.out.println("Consultando usuários na api...");
        String uri = baseUrl + "/users";

        List<UsuarioDto> usuarios = webClient.get()
                .uri(uri)
                .header("apikey", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UsuarioDto>>() {})
                .block();

        System.out.println("Finalizado consulta de usuários na api...");

        return usuarios;
    }
}
