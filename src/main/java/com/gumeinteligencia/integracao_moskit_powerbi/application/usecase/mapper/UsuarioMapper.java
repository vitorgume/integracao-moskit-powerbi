package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario paraDomain(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.id())
                .name(dto.name())
                .active(dto.active())
                .userName(dto.userName())
                .build();
    }

    public UsuarioDto paraDto(Usuario domain) {
        return UsuarioDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .active(domain.getActive())
                .userName(domain.getUserName())
                .build();
    }
}
