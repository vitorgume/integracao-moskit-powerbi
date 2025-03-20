package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
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
                .previsionCloseDate(entity.getPrevisionCloseDate())
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
                .previsionCloseDate(domain.getPrevisionCloseDate())
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
                .previsionCloseDate(MapperData.trasnformaData(dto.getPrevisionCloseDate()))
                .closeDate(MapperData.trasnformaData(dto.getCloseDate()))
                .qualificacao(MapperEnum.organizaQualificacao(dto))
                .build();
    }

//    public static NegocioDto paraDto(Negocio domain) {
//        return NegocioDto.builder()
//                .id(domain.getId())
//                .name(domain.getName())
//                .price(domain.getPrice())
//                .stage(FaseMapper.paraDto(domain.getStage()))
//                .status(domain.getStatus())
//                .responsible(UsuarioMapper.paraDto(domain.getResponsible()))
//                .createdBy(UsuarioMapper.paraDto(domain.getCreatedBy()))
//                .dateCreated(domain.getDateCreated().toString())
//                .previsionCloseDate(domain.getPrevisionCloseDate().toString())
//                .closeDate(domain.getCloseDate().toString())
//                .entityCustomFields()
//    }



}