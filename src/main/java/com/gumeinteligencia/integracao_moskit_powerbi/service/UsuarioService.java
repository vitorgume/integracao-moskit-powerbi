package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd.UsuarioDataProvider;
import com.gumeinteligencia.integracao_moskit_powerbi.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioDataProvider dataProvider;

    public Usuario consultarPorId(Integer id) {
        Optional<Usuario> usuarioOptional = dataProvider.consultarPorId(id).map(UsuarioMapper::paraDomain);

        if(usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }

        return usuarioOptional.get();
    }
}
