package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;

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
                .closeDate(entity.getCloseDate())
                .qualificacao(entity.getQualificacao())
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
                .closeDate(domain.getCloseDate())
                .qualificacao(domain.getQualificacao())
                .build();
    }

    public static Negocio paraDomainDeDto(NegocioDto dto) {
        return Negocio.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stage(FaseMapper.paraDomainDeDto(dto.getStage()))
                .status(dto.getStatus())
                .responsible(UsuarioMapper.paraDomainDeDto(dto.getResponsible()))
                .createdBy(UsuarioMapper.paraDomainDeDto(dto.getCreatedBy()))
                .dateCreated(MapperData.trasnformaData(dto.getDateCreated()))
                .closeDate(MapperData.trasnformaData(dto.getCloseDate()))
                .qualificacao(MapperEnum.organizaQualificacao(dto))
                .build();
    }

    public static NegocioDto paraDto(Negocio domain) {
        if(domain.getCloseDate() == null) {
            return NegocioDto.builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .price(domain.getPrice())
                    .stage(FaseMapper.paraDto(domain.getStage()))
                    .status(domain.getStatus())
                    .responsible(UsuarioMapper.paraDto(domain.getResponsible()))
                    .createdBy(UsuarioMapper.paraDto(domain.getCreatedBy()))
                    .dateCreated(MapperData.trasformaDataString(domain.getDateCreated()))
                    .qualificacao(domain.getQualificacao())
                    .build();
        } else {
            return NegocioDto.builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .price(domain.getPrice())
                    .stage(FaseMapper.paraDto(domain.getStage()))
                    .status(domain.getStatus())
                    .responsible(UsuarioMapper.paraDto(domain.getResponsible()))
                    .createdBy(UsuarioMapper.paraDto(domain.getCreatedBy()))
                    .dateCreated(MapperData.trasformaDataString(domain.getDateCreated()))
                    .closeDate(MapperData.trasformaDataString(domain.getCloseDate()))
                    .qualificacao(domain.getQualificacao())
                    .build();
        }


    }



}