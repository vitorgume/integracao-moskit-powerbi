package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.EmpresaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmpresaMapper {

    private final UsuarioMapper usuarioMapper;

    public Empresa paraDomain(EmpresaDto dto) {
        return Empresa.builder()
                .id(dto.id())
                .cnpj(dto.cnpj())
                .name(dto.name())
                .responsible(usuarioMapper.paraDomain(dto.responsible()))
                .createdBy(usuarioMapper.paraDomain(dto.createdBy()))
                .build();
    }

    public EmpresaDto paraDto(Empresa empresa) {
        return EmpresaDto.builder()
                .id(empresa.getId())
                .cnpj(empresa.getCnpj())
                .name(empresa.getName())
                .responsible(usuarioMapper.paraDto(empresa.getResponsible()))
                .createdBy(usuarioMapper.paraDto(empresa.getCreatedBy()))
                .build();
    }
}
