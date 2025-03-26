package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.FunilNaoEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumFunilEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.FunilGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.FunilGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.FunilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class FunilUseCase {

    private final FunilGateway gateway;
    private final FunilGatewayApi gatewayApi;

    public Funil consultarPorId(Integer id) {
        log.info("Consultando funil pelo id. Id: {}", id);

        Optional<Funil> funilOptional = gateway.consultarPorId(id);

        if(funilOptional.isEmpty()) {
            throw new FunilNaoEncontradoException(id);
        }

        Funil funil = funilOptional.get();

        log.info("Funil consultado com sucesso. Funil: {}", funil);

        return funil;
    }

    public int atualiza() {
        log.info("Começando atualizações de funis...");

        List<Funil> funisNovos = gatewayApi.consultarFunis()
                .stream()
                .map(FunilMapper::paraDomain)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        if(funisNovos.isEmpty()) {
            throw new NenhumFunilEncontradoException();
        }

        List<Funil> funisAntigos = gateway.listar();

        List<Funil> funisCadastrar = funisNovos.stream()
                .filter(funilNovo ->
                        funisAntigos.stream().noneMatch(funilAntigo ->
                                funilAntigo.getName().equals(funilNovo.getName())
                        )
                )
                .toList();

        funisCadastrar.forEach(funil -> {
            Funil funisSalvos = this.salvar(funil);
            contAtualizacoes.getAndIncrement();
            log.info("Fúnil salvo com sucesso: {}", funisSalvos);
        });


        log.info("Finalizado atualizações de funis. Funis: {}", funisCadastrar);
        log.info("Quantidade de operações: {}", contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    public Funil salvar(Funil novoFunil) {
        log.info("Salvando funil. Funil: {}", novoFunil);

        Funil funilSalvo = gateway.salvar(novoFunil);

        log.info("Funil salvo com sucesso. Funil: {}", funilSalvo);

        return funilSalvo;
    }
}
