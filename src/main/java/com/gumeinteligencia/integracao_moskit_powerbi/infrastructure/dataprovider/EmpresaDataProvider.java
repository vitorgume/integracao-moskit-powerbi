package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.EmpresaEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmpresaDataProvider {

    private final EmpresaRepository repository;

    public List<EmpresaEntity> listarEmpresas() {
        List<EmpresaEntity> empresaEntities;

        try {
            empresaEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return empresaEntities;
    }

    public EmpresaEntity salvar(EmpresaEntity empresaEntity) {
        EmpresaEntity empresaSalva;

        try {
            empresaSalva = repository.save(empresaEntity);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return empresaSalva;
    }
}
