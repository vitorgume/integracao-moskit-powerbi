package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacoesNegocios;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.MovimentacoesNegociosDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.MovimentacaoNegociosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacoesNegociosService {

    private final MovimentacoesNegociosDataProvider dataProvider;

    public List<MovimentacoesNegocios> listar() {
        return dataProvider.listar().stream().map(MovimentacaoNegociosMapper::paraDomain).toList();
    }
}
