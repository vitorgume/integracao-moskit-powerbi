package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NegocioNaoEncontradoExcpetion;
import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NenhumNegocioEncontradoException;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.NegocioGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.NegocioGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.CampoPersonalizadoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class NegocioUseCase {

    private final NegocioGateway gateway;
    private final NegocioGatewayApi gatewayApi;
    private final FaseUseCase faseUseCase;
    private final FunilUseCase funilUseCase;
    private final UsuarioUseCase usuarioUseCase;

    public List<Negocio> listar() {
        log.info("Listando negócios...");

        List<Negocio> negocioList = gateway.listar();

        log.info("Listagem de negócios finalizada. Lista: {}", negocioList);

        return negocioList;
    }

    public Negocio consultarPorId(Integer id) {
        log.info("Consultando negócio por id. Id: {}", id);
        Optional<Negocio> negocioOptional = gateway.consultarPorId(id);

        if(negocioOptional.isEmpty()) {
            throw new NegocioNaoEncontradoExcpetion();
        }

        Negocio negocio = negocioOptional.get();

        log.info("Consulta de negócio concluida com sucesso. Negócio: {}", negocio);

        return negocio;
    }

    public int atualiza() {
        log.info("Começando atualização de negócios...");
        List<NegocioDto> negociosDtos = gatewayApi.consultaNegocios();

        List<Negocio> negociosNovos = negociosDtos.stream()
                .peek(negocio -> {
                    List<CampoPersonalizadoDto> camposPersonalizado = negocio.getEntityCustomFields()
                            .stream()
                            .filter(campoPersonalizado -> campoPersonalizado.id().equals("CF_8P5q4Vi6ioJ7lmRJ"))
                            .toList();

                    negocio.setEntityCustomFields(camposPersonalizado);
                })
                .toList()
                .stream()
                .map(this::buscaDadosNecessarios)
                .map(NegocioMapper::paraDomain)
                .toList();

        AtomicInteger contAtualizacoes = new AtomicInteger();

        if (negociosNovos.isEmpty()) {
            throw new NenhumNegocioEncontradoException();
        }

        List<Negocio> negociosAntigos = gateway.listar();

        List<Negocio> negociosCadastrar = negociosNovos.stream()
                .filter(negocioNovo ->
                        negociosAntigos.stream().noneMatch(negocioAntigo ->
                                negocioAntigo.getName().equals(negocioNovo.getName())
                        )
                )
                .toList();

        negociosCadastrar.forEach(negocio -> {
            Usuario usuario = usuarioUseCase.consultarPorId(negocio.getResponsible().getId());
            Fase fase = faseUseCase.consultarPorId(negocio.getStage().getId());

            negocio.setResponsible(usuario);
            negocio.setCreatedBy(usuario);
            negocio.setStage(fase);

            Negocio negocioSalvo = this.salvar(negocio);
            contAtualizacoes.getAndIncrement();
            log.info("Negócio atualizado com sucesso: {}", negocioSalvo);
        });

        log.info("Finalizado atualizações de negócios. Atualizações: {}", negociosCadastrar);
        log.info("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    public Negocio salvar(Negocio novoNegocio) {
        log.info("Salvando novo negócio. Negócio: {}", novoNegocio);

        Negocio negocioSalvo = gateway.salvar(novoNegocio);

        log.info("Negócio salvo com sucesso. Negócio: {}", negocioSalvo);

        return negocioSalvo;
    }

    private NegocioDto buscaDadosNecessarios(NegocioDto negocio) {
        Fase fase = faseUseCase.consultarPorId(negocio.getStage().getId());
        Funil funilStage = funilUseCase.consultarPorId(fase.getPipeline().getId());
        negocio.getStage().setPipeline(FunilMapper.paraDto(funilStage));
        return negocio;
    }
}
