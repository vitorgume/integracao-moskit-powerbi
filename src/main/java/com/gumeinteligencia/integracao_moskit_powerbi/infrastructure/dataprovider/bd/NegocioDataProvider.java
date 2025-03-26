package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.NegocioGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.NegocioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.NegocioRepository;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.NegocioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class NegocioDataProvider implements NegocioGateway {

    private final NegocioRepository repository;
    private final String MENSAGEM_ERRO_LISTAR_NEGOCIOS = "Erro ao listar negócios.";
    private final String MENSAGEM_ERRO_SALVAR_NEGOCIO = "Erro ao salvar negócio.";
    private final String MENSAGEM_ERRO_CONSULTAR_NEGOCIO_POR_ID = "Erro ao consultar negócio por id.";


    @Override
    public List<Negocio> listar() {
        List<NegocioEntity> negociosEntities;

        try {
            negociosEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_NEGOCIOS, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR_NEGOCIOS, ex.getCause());
        }

        return negociosEntities.stream().map(NegocioMapper::paraDomain).toList();
    }

    @Override
    public Negocio salvar(Negocio negocio) {
        NegocioEntity negocioSalvo;

        try {
            negocioSalvo = repository.save(NegocioMapper.paraEntity(negocio));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_NEGOCIO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_NEGOCIO, ex.getCause());
        }

        return NegocioMapper.paraDomain(negocioSalvo);
    }

    public Optional<Negocio> consultarPorId(Integer id) {
        Optional<NegocioEntity> negocioEntity;

        try {
            negocioEntity = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_NEGOCIO_POR_ID, ex);
            throw new RuntimeException(MENSAGEM_ERRO_CONSULTAR_NEGOCIO_POR_ID, ex.getCause());
        }

        return negocioEntity.map(NegocioMapper::paraDomain);
    }
}
