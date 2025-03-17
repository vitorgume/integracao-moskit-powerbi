package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.UsuarioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioDataProvider {

    private final UsuarioRepository repository;

    public List<UsuarioEntity> listarUsuarios() {
        List<UsuarioEntity> usuarioEntities;

        try {
            usuarioEntities = repository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return usuarioEntities;
    }

    public UsuarioEntity salvar(UsuarioEntity usuarioEntity) {
        UsuarioEntity usuarioSalvo;

        try {
            usuarioSalvo = repository.save(usuarioEntity);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return usuarioSalvo;
    }

    public Optional<UsuarioEntity> consultarPorId(Integer id) {
        Optional<UsuarioEntity> usuarioEntity;

        try {
            usuarioEntity = repository.findById(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return usuarioEntity;
    }
}
