package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.AtividadeGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Atividade;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.AtividadeMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.AtividadeEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.AtividadesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AtividadeDataProvider implements AtividadeGateway {

    private final AtividadesRepository repository;
    private final String MENSAGEM_ERRO_LISTAGEM_ATIVIDADE = "Erro ao listar atividades.";
    private final String MENSAGEM_ERRO_SALVAR_ATIVIDADE = "Erro ao salvar atividade.";

    @Override
    public List<Atividade> listar() {
        List<AtividadeEntity> atividadeEntityList;

        try {
            atividadeEntityList = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAGEM_ATIVIDADE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAGEM_ATIVIDADE, ex.getCause());
        }

        return atividadeEntityList.stream().map(AtividadeMapper::paraDomain).toList();
    }

    @Override
    public Atividade salvar(Atividade atividade) {
        AtividadeEntity atividadeSalva;

        try {
            atividadeSalva = repository.save(AtividadeMapper.paraEntity(atividade));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_ATIVIDADE, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_ATIVIDADE, ex.getCause());
        }

        return AtividadeMapper.paraDomain(atividadeSalva);
    }
}
