package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.UsuarioDto;

public class UsuarioMapper {

    public static Usuario paraDomain(UsuarioDto dto) {
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
