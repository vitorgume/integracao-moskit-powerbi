package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.AtividadeNegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.AtividadesNegocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AtividadeNegocioDataProvider {

    private final AtividadesNegocioRepository repository;


    public List<AtividadeNegocioEntity> listar() {
        List<AtividadeNegocioEntity> atividadeNegocioEntityList;

        try {
            atividadeNegocioEntityList = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return atividadeNegocioEntityList;
    }

    public AtividadeNegocioEntity salvar(AtividadeNegocioEntity atividadeNegocioEntity) {
        AtividadeNegocioEntity atividadeSalva;

        try {
            atividadeSalva = repository.save(atividadeNegocioEntity);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return atividadeSalva;
    }
}
