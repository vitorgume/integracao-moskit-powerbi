package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FaseEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.FaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FaseDataProvider {

    private final FaseRepository repository;


    public List<FaseEntity> listarUsuario() {
        List<FaseEntity> fasesEntities;

        try {
            fasesEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return fasesEntities;
    }

    public FaseEntity salvar(FaseEntity faseEntity) {
        FaseEntity faseSalva;

        try {
            faseSalva = repository.save(faseEntity);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return faseSalva;
    }
}
