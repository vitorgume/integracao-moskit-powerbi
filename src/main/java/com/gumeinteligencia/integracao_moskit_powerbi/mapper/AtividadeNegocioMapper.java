package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.AtividadeNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.AtividadeNegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.AtividadeNegocioDto;

public class AtividadeNegocioMapper {

    /*
     private Integer id;
    private String title;
    private Usuario createdBy;
    private Usuario responsible;
    private Usuario doneUser;
    private LocalDate dateCreated;
    private LocalDate dueDate;
    private LocalDate doneDate;
    private TipoAtividade type;
    private Integer duration;
    private Integer totalTries;
    private List<Negocio> deals;
    private List<Empresa> companies;
     */


    public static AtividadeNegocio paraDomain(AtividadeNegocioEntity entity) {
        return AtividadeNegocio.builder()
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

    public static AtividadeNegocioEntity paraEntity(AtividadeNegocio domain) {
        return AtividadeNegocioEntity.builder()
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

    public static AtividadeNegocio paraDomainDeDto(AtividadeNegocioDto dto) {
        return AtividadeNegocio.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .createdBy(UsuarioMapper.paraDomainDeDto(dto.getCreatedBy()))
                .responsible(UsuarioMapper.paraDomainDeDto(dto.getResponsible()))
                .doneUser(UsuarioMapper.paraDomainDeDto(dto.getDoneUser()))
                .dateCreated(MapperData.trasnformaData(dto.getDateCreated()))
                .dueDate(MapperData.trasnformaData(dto.getDueDate()))
                .doneDate(MapperData.trasnformaData(dto.getDoneDate()))
                .type(dto.getType())
                .duration(dto.getDuration())
                .totalTries(dto.getTotalTries())
                .deals(dto.getDeals().stream().map(NegocioMapper::paraDomainDeDto).toList())
                .companies(dto.getCompanies().stream().map(EmpresaMapper::paraDomainDeDto).toList())
                .build();
    }
}
