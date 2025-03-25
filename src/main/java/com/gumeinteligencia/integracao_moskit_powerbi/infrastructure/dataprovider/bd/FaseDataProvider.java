package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.FaseMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.FaseRepository;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.FaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FaseDataProvider {

    private final FaseRepository repository;
    private final String MENSAGEM_ERRO_LISTAR_FASES = "Erro ao listar fases.";
    private final String MENSAGEM_ERRO_SALVAR_FASE = "Erro ao salvar fase.";
    private final String MENSAGEM_ERRO_CONSULTAR_FASE_POR_ID = "Erro ao consultar fase por seu id.";


    public List<Fase> listarFases() {
        List<FaseEntity> fasesEntities;

        try {
            fasesEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAR_FASES, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAR_FASES, ex.getCause());
        }

        return fasesEntities.stream().map(FaseMapper::paraDomain).toList();
    }

    public Fase salvar(Fase fase) {
        FaseEntity faseSalva;

        try {
            faseSalva = repository.save(FaseMapper.paraEntity(fase));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_FASE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_FASE, ex.getCause());
        }

        return FaseMapper.paraDomain(faseSalva);
    }

    public Optional<Fase> consultarPorId(Integer id) {
        Optional<FaseEntity> faseEntity;

        try {
            faseEntity = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_FASE_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_FASE_POR_ID, ex.getCause());
        }

        return faseEntity.map(FaseMapper::paraDomain);
    }
}
