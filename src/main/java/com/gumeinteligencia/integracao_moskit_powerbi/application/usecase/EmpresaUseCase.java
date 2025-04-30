package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.EmpresaNaoEncontradaException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.EmpresaGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.EmpresaGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.EmpresaMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaUseCase {

    private final EmpresaGateway gateway;
    private final EmpresaGatewayApi empresaGatewayApi;
    private final UsuarioUseCase usuarioUseCase;
    private final EmpresaMapper empresaMapper;

    public Empresa consultarPorId(Integer id) {
        log.info("Consultando empresa pelo id. Id: {}", id);

        Optional<Empresa> empresaOptional = gateway.consultarPorId(id);

        if(empresaOptional.isEmpty()) {
            throw new EmpresaNaoEncontradaException(id);
        }

        Empresa empresa = empresaOptional.get();

        log.info("Empresa consultada com sucesso. Empresa: {}",empresa);

        return empresa;
    }

    public int atualiza() {
        log.info("Começando atualizações de empresas...");

        List<Empresa> empresasNovas = empresaGatewayApi.consultaEmpresas()
                .stream()
                .map(empresaMapper::paraDomain)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        List<Empresa> empresasAntigas = gateway.listar();

        List<Empresa> empresasCadastrar = empresasNovas.stream()
                .filter(empresaNova ->
                        empresasAntigas.stream().noneMatch(empresaAntiga ->
                                empresaAntiga.getId().equals(empresaNova.getId())
                        )
                )
                .toList();

        empresasCadastrar.forEach(empresa -> {
            Usuario usuario = usuarioUseCase.consultarPorId(empresa.getResponsible().getId());
            empresa.setResponsible(usuario);
            empresa.setCreatedBy(usuario);
            Empresa empresasSalva = this.salvar(empresa);
            contAtualizacoes.getAndIncrement();
            log.info("Empresa atualizada com sucesso: {}", empresasSalva);
        });

        log.info("Finalizado atualizações de empresas. Empresas: {}", empresasCadastrar);
        log.info("Quantidade de operações: {}", contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    public Empresa salvar(Empresa novaEmpresa) {
        log.info("Salvando empresa. Empresa: {}", novaEmpresa);

        Empresa empresaSalva = gateway.salvar(novaEmpresa);

        log.info("Empresa salva com sucesso. Empresa: {}", empresaSalva);

        return empresaSalva;
    }
}
