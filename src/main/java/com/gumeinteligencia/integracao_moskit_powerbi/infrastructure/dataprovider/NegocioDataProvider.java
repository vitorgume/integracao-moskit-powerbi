package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.NegocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NegocioDataProvider {

    private final NegocioRepository repository;

    public List<NegocioEntity> listarNegocios() {
        List<NegocioEntity> negociosEntities;

        try {
            negociosEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return negociosEntities;
    }

    public NegocioEntity salvar(NegocioEntity negocioEntity) {
        NegocioEntity negocioSalvo;

        try {
            negocioSalvo = repository.save(negocioEntity);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return negocioSalvo;
    }

    public Optional<NegocioEntity> consultarPorId(Integer id) {
        Optional<NegocioEntity> negocioEntity;

        try {
            negocioEntity = repository.findById(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return negocioEntity;
    }
}
