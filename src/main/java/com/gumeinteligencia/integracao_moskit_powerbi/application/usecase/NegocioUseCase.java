package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions.NegocioNaoEncontradoExcpetion;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api.NegocioGatewayApi;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.NegocioDashBoardGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.NegocioGateway;
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NegocioUseCase {

    private final NegocioGateway gateway;
    private final NegocioGatewayApi gatewayApi;
    private final NegocioDashBoardGateway gatewayDashBoard;
    private final FaseUseCase faseUseCase;
    private final FunilUseCase funilUseCase;
    private final UsuarioUseCase usuarioUseCase;
    private final NegocioMapper mapper;
    private final FunilMapper funilMapper;
    private final AtomicInteger contAtualizacoes = new AtomicInteger();

    public List<Negocio> listar() {
        log.info("Listando negócios...");

        List<Negocio> negocioList = gateway.listar();

        log.info("Listagem de negócios finalizada. Lista: {}", negocioList);

        return negocioList;
    }

    public Negocio consultarPorId(Integer id) {
        log.info("Consultando negócio por id. Id: {}", id);
        Optional<Negocio> negocioOptional = gateway.consultarPorId(id);

        if (negocioOptional.isEmpty()) {
            throw new NegocioNaoEncontradoExcpetion();
        }

        Negocio negocio = negocioOptional.get();

        log.info("Consulta de negócio concluida com sucesso. Negócio: {}", negocio);

        return negocio;
    }

    public int atualiza() {
        log.info("Começando atualização de negócios...");
        List<Negocio> negociosApi = gatewayApi.consultaNegocios()
                .stream()
                .map(this::buscaDadosNecessarios)
                .map(mapper::paraDomain)
                .toList();


        List<Negocio> negociosBancoDados = gateway.listar();

        cadastrarNovosNegocios(negociosApi, negociosBancoDados);

        //Apenas no MVP esse trecho (79 - 85)
        negociosApi = gatewayApi.consultarTodosNegocis()
                .stream()
                .map(this::buscaDadosNecessarios)
                .map(mapper::paraDomain)
                .toList();

        atualizaNegocioLocal(negociosApi, negociosBancoDados);


        log.info("Quantidade de operações: " + contAtualizacoes.get());
        return contAtualizacoes.get();
    }

    public Negocio salvar(Negocio novoNegocio) {
        log.info("Salvando novo negócio. Negócio: {}", novoNegocio);

        Negocio negocioSalvo = gateway.salvar(novoNegocio);
        gatewayDashBoard.salvar(novoNegocio);

        log.info("Negócio salvo com sucesso. Negócio: {}", negocioSalvo);

        return negocioSalvo;
    }

    public Negocio atualizaNegocio(Negocio negocioAtualizado) {
        log.info("Atualizando status negócio pelo id. Id: {}", negocioAtualizado.getId());

        Negocio negocio = this.consultarPorId(negocioAtualizado.getId());

        negocio.atualizaDados(negocioAtualizado);

        log.info("Status do negócio atualizado com sucesso. Negócio: {}", negocio);
        contAtualizacoes.getAndIncrement();

        return this.salvar(negocio);
    }

    //Apenas no MVP
    private void atualizaNegocioLocal(List<Negocio> negociosApi, List<Negocio> negociosBancoDados) {
        log.info("Atualizando negócios ja existenes...");

        Map<Integer, Negocio> negocioBancoMap = negociosBancoDados.stream()
                .collect(Collectors.toMap(Negocio::getId, Function.identity()));

        List<Negocio> negociosAtualizar = negociosApi.stream()
                .filter(negocioApi -> {
                    Negocio negocioBd = negocioBancoMap.get(negocioApi.getId());
                    return negocioBd != null
                            && !negocioApi.equals(negocioBd);
                })
                .toList();

        negociosAtualizar.forEach(negocioApi -> {
            Negocio negocioBd = negocioBancoMap.get(negocioApi.getId());
            if (negocioBd != null) {
                negocioBd.atualizaDados(negocioApi);
                this.salvar(negocioBd);
                contAtualizacoes.getAndIncrement();
            }
        });

        log.info("Atualização de negócios ja existentes finalizada.");
    }


    private void cadastrarNovosNegocios(List<Negocio> negociosApi, List<Negocio> negociosBancoDados) {
        List<Negocio> negociosCadastrar = negociosApi.stream()
                .filter(negocioNovo ->
                        negociosBancoDados.stream().noneMatch(negocioAntigo ->
                                negocioAntigo.getId().equals(negocioNovo.getId())
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
    }

    private NegocioDto buscaDadosNecessarios(NegocioDto negocio) {
        Fase fase = faseUseCase.consultarPorId(negocio.getStage().getId());
        Funil funilStage = funilUseCase.consultarPorId(fase.getPipeline().getId());
        negocio.getStage().setPipeline(funilMapper.paraDto(funilStage));
        return negocio;
    }
}
