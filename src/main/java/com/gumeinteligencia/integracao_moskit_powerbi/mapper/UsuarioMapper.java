package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.UsuarioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.UsuarioDto;

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

    public static Usuario paraDomainDeDto(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.id())
                .name(dto.name())
                .active(dto.active())
                .userName(dto.userName())
                .build();
    }

    public static UsuarioDto paraDto(Usuario domain) {
        return UsuarioDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .active(domain.getActive())
                .userName(domain.getUserName())
                .build();
    }
}
