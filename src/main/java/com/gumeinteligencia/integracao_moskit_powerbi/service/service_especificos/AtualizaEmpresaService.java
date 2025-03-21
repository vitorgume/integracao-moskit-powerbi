package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.EmpresaDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.EmpresaMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.service.UsuarioService;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.EmpresaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtualizaEmpresaService implements Atualiza<EmpresaDto>{

    @Value("${moskit.api.key}")
    private final String apiKey;

    @Value("${moskit.api.base-url}")
    private final String baseUrl;

    private final WebClient webClient;

    private final EmpresaDataProvider dataProvider;

    private final UsuarioService usuarioService;

    public AtualizaEmpresaService(
            @Value("${moskit.api.key}") String apiKey,
            @Value("${moskit.api.base-url}") String baseUrl,
            WebClient webClient,
            EmpresaDataProvider dataProvider,
            UsuarioService usuarioService
    ) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.webClient = webClient;
        this.dataProvider = dataProvider;
        this.usuarioService = usuarioService;
    }

    @Override
    public int atualiza() {
        System.out.println("Começando atualizações de empresas...");
        List<Empresa> empresasNovas = consultaApi().stream().map(EmpresaMapper::paraDomainDeDto).toList();
        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(empresasNovas.isEmpty()) {
            throw new RuntimeException("Nenhuma empresa encontrado");
        }

        List<Empresa> empresasAntigas = dataProvider.listarEmpresas().stream().map(EmpresaMapper::paraDomain).toList();

        List<Empresa> empresasCadastrar = empresasNovas.stream()
                .filter(empresaNova ->
                        empresasAntigas.stream().noneMatch(empresaAntiga ->
                                empresaAntiga.getName().equals(empresaNova.getName())
                        )
                )
                .toList();

        empresasCadastrar.forEach(empresa -> {
            Usuario usuario = usuarioService.consultarPorId(empresa.getResponsible().getId());
            empresa.setResponsible(usuario);
            empresa.setCreatedBy(usuario);
            Empresa empresasSalvas = EmpresaMapper.paraDomain(dataProvider.salvar(EmpresaMapper.paraEntity(empresa)));
            contAtualizacoes.getAndIncrement();
            System.out.println("Empresa salva com sucesso: " + empresasSalvas.toString());
        });

        System.out.println("Finalizado atualizações de empresas...");
        System.out.println("Quantidade de operações: " + contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    @Override
    public List<EmpresaDto> consultaApi() {
        System.out.println("Consultando empresas na api...");
        String uri = baseUrl + "/companies";
        String nextPageToken = null;
        int quantity = 50;
        List<EmpresaDto> todasEmpresas = new ArrayList<>();

        do {
            String uriPaginada = nextPageToken == null
                    ? uri + "?quantity=" + quantity
                    : uri + "?quantity=" + quantity + "&nextPageToken=" + nextPageToken;

            var response = webClient.get()
                    .uri(uriPaginada)
                    .header("apikey", apiKey)
                    .retrieve()
                    .toEntityList(EmpresaDto.class)
                    .block();

            if (response != null && response.getBody() != null) {
                todasEmpresas.addAll(response.getBody());
            }

            nextPageToken = response != null ? response.getHeaders().getFirst("X-Moskit-Listing-Next-Page-Token") : null;

        } while (nextPageToken != null && !nextPageToken.isEmpty());

        System.out.println("Finalizado consulta de empresas na api...");
        return todasEmpresas;
    }


}
