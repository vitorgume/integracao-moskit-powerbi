package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacaoNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.MovimentacaoNegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.MovimentacaoNegocioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoNegocioService {

    private final MovimentacaoNegocioDataProvider dataProvider;

    public List<MovimentacaoNegocio> listar() {
        return dataProvider.listar().stream().map(MovimentacaoNegocioMapper::paraDomain).toList();
    }
}
