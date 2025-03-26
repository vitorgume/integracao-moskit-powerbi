package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.FaseNaoEncontradaException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumaFaseEncontradaException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.FaseGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.FaseGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.FaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaseUseCase {

    private final FaseGateway gateway;
    private final FaseGatewayApi gatewayApi;
    private final FunilUseCase funilUseCase;

    public Fase consultarPorId(Integer id) {
        log.info("Consultando fase pelo seu id. Id: {}", id);

        Optional<Fase> faseOptional = gateway.consultarPorId(id);

        if(faseOptional.isEmpty()) {
            throw new FaseNaoEncontradaException(id);
        }

        Fase fase = faseOptional.get();

        log.info("Fase consultada com sucesso. Fase: {}", fase);

        return fase;
    }

    public int atualiza() {
        log.info("Começando atualização de fases...");

        List<Fase> faseNovas = gatewayApi.consultaFases()
                .stream()
                .map(FaseMapper::paraDomain)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(faseNovas.isEmpty()) {
            throw new NenhumaFaseEncontradaException();
        }

        List<Fase> fasesAntigas = gateway.listar();

        List<Fase> fasesCadastrar = faseNovas.stream()
                .filter(faseNovo ->
                        fasesAntigas.stream().noneMatch(faseAntiga ->
                                faseAntiga.getName().equals(faseNovo.getName())
                        )
                )
                .toList();

        fasesCadastrar.forEach(fase -> {
            Funil funil = funilUseCase.consultarPorId(fase.getPipeline().getId());
            fase.setPipeline(funil);
            Fase fasesSalva = this.salvar(fase);
            contAtualizacoes.getAndIncrement();
            log.info("Fase atualizada com sucesso: {}",fasesSalva);
        });

        log.info("Atualização de fases finalizada. Fases: {}", fasesCadastrar);
        log.info("Quantidade de operações: {}", contAtualizacoes.get());

        return contAtualizacoes.get();
    }

    public Fase salvar(Fase novaFase) {
        log.info("Salvando fase. Fase: {}", novaFase);

        Fase faseSalva = gateway.salvar(novaFase);

        log.info("Fase salva com sucesso. {}", faseSalva);

        return faseSalva;
    }
}
