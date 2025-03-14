package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.UsuarioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaNegocioService implements Atualiza<NegocioDto>{

//    @Value("${moskit.api.key}")
//    private final String apiKey;
//
//    @Value("${moskit.api.base-url}")
//    private final String baseUrl;
//
//    private final WebClient webClient;
//
//    private final NegocioDataProvider dataProvider;
//
//    public AtualizaNegocioService(
//            @Value("${moskit.api.key}") String apiKey,
//            @Value("${moskit.api.base-url}") String baseUrl,
//            WebClient webClient,
//            NegocioDataProvider dataProvider
//    ) {
//        this.apiKey = apiKey;
//        this.baseUrl = baseUrl;
//        this.webClient = webClient;
//        this.dataProvider = dataProvider;
//    }

    @Override
    public int atualiza() {
//        List<Usuario> usuariosNovos = consultaApi().stream().map(UsuarioMapper::paraDomainDeDto).toList();
//        AtomicInteger contAtualizacoes = new AtomicInteger();
//
//        if(usuariosNovos.isEmpty()) {
//            throw new RuntimeException("Nenhum usuário encontrado");
//        }
//
//        List<Usuario> usuariosAntigos = dataProvider.listarUsuario().stream().map(UsuarioMapper::paraDomain).toList();
//
//        List<Usuario> usuariosCadastrar = usuariosNovos.stream()
//                .filter(usuarioNovo ->
//                        usuariosAntigos.stream().noneMatch(usuarioAntigo ->
//                                usuarioAntigo.getName().equals(usuarioNovo.getName())
//                        )
//                )
//                .toList();
//
//        usuariosCadastrar.forEach(usuario -> {
//            Usuario usuarioSalvo = UsuarioMapper.paraDomain(dataProvider.salvar(UsuarioMapper.paraEntity(usuario)));
//            contAtualizacoes.getAndIncrement();
//            System.out.println("Usuario salvo com sucesso: " + usuarioSalvo);
//        });
//
//
//        return contAtualizacoes.get();
//
//        List<Negocio> negociosNovos = consultaApi().stream().map(NegocioMapper::paraDomainDeDto).toList();
        return 0;
    }

    @Override
    public List<NegocioDto> consultaApi() {
//        String uri = baseUrl + "/deals";
//
//        List<NegocioDto> negocios = webClient.get()
//                .uri(uri)
//                .header("apikey", apiKey)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<NegocioDto>>() {})
//                .block();
//
//        return negocios;
        return null;
    }
}
