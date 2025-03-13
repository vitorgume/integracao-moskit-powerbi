package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.UsuarioEntity;

public class UsuarioMapper {

    public static Usuario paraDomain(UsuarioEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .name(entity.getName())
                .active(entity.getActive())
                .userName(entity.getUserName())
                .build();
    }

    public static UsuarioEntity paraEntity(Usuario domain) {
        return UsuarioEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .active(domain.getActive())
                .userName(domain.getUserName())
                .build();
    }
}
