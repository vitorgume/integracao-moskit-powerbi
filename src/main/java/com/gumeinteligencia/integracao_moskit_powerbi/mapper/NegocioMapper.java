package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class NegocioMapper {

    public static Negocio paraDomain(NegocioEntity entity) {
        return Negocio.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stage(FaseMapper.paraDomain(entity.getStage()))
                .status(entity.getStatus())
                .responsible(UsuarioMapper.paraDomain(entity.getResponsible()))
                .createdBy(UsuarioMapper.paraDomain(entity.getCreatedBy()))
                .dateCreated(entity.getDateCreated())
                .previsionCloseDate(entity.getPrevisionCloseDate())
                .closeDate(entity.getCloseDate())
                .build();
    }

    public static NegocioEntity paraEntity(Negocio domain) {
        return NegocioEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice())
                .stage(FaseMapper.paraEntity(domain.getStage()))
                .status(domain.getStatus())
                .responsible(UsuarioMapper.paraEntity(domain.getResponsible()))
                .createdBy(UsuarioMapper.paraEntity(domain.getCreatedBy()))
                .dateCreated(domain.getDateCreated())
                .previsionCloseDate(domain.getPrevisionCloseDate())
                .closeDate(domain.getCloseDate())
                .build();
    }

    public static Negocio paraDomainDeDto(NegocioDto dto) {
        return Negocio.builder()
                .id(dto.id())
                .name(dto.name())
                .price(dto.price())
                .stage(FaseMapper.paraDomainDeDto(dto.stage()))
                .status(dto.status())
                .responsible(UsuarioMapper.paraDomainDeDto(dto.responsible()))
                .createdBy(UsuarioMapper.paraDomainDeDto(dto.createdBy()))
                .dateCreated(trasnformaData(dto.dateCreated()))
                .previsionCloseDate(trasnformaData(dto.previsionCloseDate()))
                .closeDate(trasnformaData(dto.closeDate()))
                .build();
    }

    private static LocalDate trasnformaData(String data) {
        if(data != null) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(data);
            LocalDate dataTransformada = offsetDateTime.toLocalDate();
            return dataTransformada;
        } else {
            return null;
        }
    }
}