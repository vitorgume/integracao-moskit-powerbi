package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Atividade;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.AtividadeEntity;

public class AtividadeMapper {

    public static Atividade paraDomain(AtividadeEntity entity) {
        return Atividade.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .createdBy(UsuarioMapper.paraDomain(entity.getCreatedBy()))
                .responsible(UsuarioMapper.paraDomain(entity.getResponsible()))
                .doneUser(UsuarioMapper.paraDomain(entity.getDoneUser()))
                .dateCreated(entity.getDateCreated())
                .dueDate(entity.getDueDate())
                .doneDate(entity.getDoneDate())
                .type(entity.getType())
                .duration(entity.getDuration())
                .totalTries(entity.getTotalTries())
                .deals(entity.getDeals().stream().map(NegocioMapper::paraDomain).toList())
                .companies(entity.getCompanies().stream().map(EmpresaMapper::paraDomain).toList())
                .build();
    }

    public static AtividadeEntity paraEntity(Atividade domain) {
        return AtividadeEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .createdBy(UsuarioMapper.paraEntity(domain.getCreatedBy()))
                .responsible(UsuarioMapper.paraEntity(domain.getResponsible()))
                .doneUser(UsuarioMapper.paraEntity(domain.getDoneUser()))
                .dateCreated(domain.getDateCreated())
                .dueDate(domain.getDueDate())
                .doneDate(domain.getDoneDate())
                .type(domain.getType())
                .duration(domain.getDuration())
                .totalTries(domain.getTotalTries())
                .deals(domain.getDeals().stream().map(NegocioMapper::paraEntity).toList())
                .companies(domain.getCompanies().stream().map(EmpresaMapper::paraEntity).toList())
                .build();
    }
}
