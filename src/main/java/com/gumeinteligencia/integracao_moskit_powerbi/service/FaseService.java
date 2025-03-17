package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Fase;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.FaseDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaseService {

    private final FaseDataProvider dataProvider;

    public Fase consultarPorId(Integer id) {
        Optional<Fase> fase = dataProvider.consultarPorId(id).map(FaseMapper::paraDomain);

        if(fase.isEmpty()) {
            throw new RuntimeException("Fase não encontrada com o id: " + id);
        }

        return fase.get();
    }
}
