package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.EmpresaDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.EmpresaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaDataProvider dataProvider;

    public Empresa consultarPorId(Integer id) {
        Optional<Empresa> empresa = dataProvider.consultarPorId(id).map(EmpresaMapper::paraDomain);

        if(empresa.isEmpty()) {
            throw new RuntimeException("Empresa não encontrada com o id: " + id);
        }

        return empresa.get();
    }
}
