package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FunilEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.FunilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FunilDataProvider {

    private final FunilRepository repository;

    public List<FunilEntity> listarFunis() {
        List<FunilEntity> funisEntities;

        try {
            funisEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return funisEntities;
    }

    public FunilEntity salvar(FunilEntity funilEntity) {
        FunilEntity funilSalvo;

        try {
            funilSalvo = repository.save(funilEntity);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return funilSalvo;
    }

    public Optional<FunilEntity> consultarPorId(Integer id) {
        Optional<FunilEntity> funilEntity;

        try {
            funilEntity = repository.findById(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return funilEntity;
    }
}
