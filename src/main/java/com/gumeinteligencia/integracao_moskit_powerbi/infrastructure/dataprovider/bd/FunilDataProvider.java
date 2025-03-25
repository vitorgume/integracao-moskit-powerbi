package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.FunilMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.FunilEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.FunilRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FunilDataProvider {

    private final FunilRepository repository;
    private final String MENSAGEM_ERRO_LISTAGEM_FUNIS = "Erro ao listar funis.";
    private final String MENSAGEM_ERRO_SALVAR_FUNIL = "Erro ao salvar funil.";
    private final String MENSAGEM_ERRO_CONSULTAR_FUNIL_POR_ID = "Erro ao consultar funil por id.";

    public List<Funil> listarFunis() {
        List<FunilEntity> funisEntities;

        try {
            funisEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAGEM_FUNIS, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAGEM_FUNIS, ex.getCause());
        }

        return funisEntities.stream().map(FunilMapper::paraDomain).toList();
    }

    public Funil salvar(Funil funil) {
        FunilEntity funilSalvo;

        try {
            funilSalvo = repository.save(FunilMapper.paraEntity(funil));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_FUNIL, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_FUNIL, ex.getCause());
        }

        return FunilMapper.paraDomain(funilSalvo);
    }

    public Optional<Funil> consultarPorId(Integer id) {
        Optional<FunilEntity> funilEntity;

        try {
            funilEntity = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_FUNIL_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_FUNIL_POR_ID, ex.getCause());
        }

        return funilEntity.map(FunilMapper::paraDomain);
    }
}
