package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.NegocioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.NegocioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NegocioService {

    private final NegocioDataProvider dataProvider;


    public List<Negocio> listar() {
        return dataProvider.listarNegocios().stream().map(NegocioMapper::paraDomain).toList();
    }

    public Negocio consultarPorId(Integer id) {
        Optional<NegocioEntity> negocio = dataProvider.consultarPorId(id);

        if(negocio.isEmpty()) {
            throw new RuntimeException("Negócio não encontrado com id: " + id);
        }

        return NegocioMapper.paraDomain(negocio.get());
    }
}
