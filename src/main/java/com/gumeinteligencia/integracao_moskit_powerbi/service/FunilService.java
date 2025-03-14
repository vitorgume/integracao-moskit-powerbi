package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Funil;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.FunilDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FunilEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.FunilMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FunilService {

    private final FunilDataProvider dataProvider;

    public Funil consultarPorId(Integer id) {
        Optional<FunilEntity> funilOptional = dataProvider.consultarPorId(id);

        if(funilOptional.isEmpty()) {
            throw new RuntimeException("Fúnil não encontrado com id: " + id);
        }

        return FunilMapper.paraDomain(funilOptional.get());
    }
}
