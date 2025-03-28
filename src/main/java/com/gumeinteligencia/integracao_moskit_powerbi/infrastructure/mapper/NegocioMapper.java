package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.NegocioEntity;

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
                .motivoPerda(entity.getMotivoPerda())
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
                .motivoPerda(domain.getMotivoPerda())
                .build();
    }
}