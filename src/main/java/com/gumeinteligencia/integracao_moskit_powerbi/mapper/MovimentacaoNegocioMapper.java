package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacaoNegocio;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.MovimentacaoNegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.MovimentacaoNegociosDto;

public class MovimentacaoNegocioMapper {

    public static MovimentacaoNegocio paraDomain(MovimentacaoNegocioEntity entity) {

        if(entity.getFaseAntiga() == null) {
            return MovimentacaoNegocio.builder()
                    .id(entity.getId())
                    .negocio(NegocioMapper.paraDomain(entity.getNegocio()))
                    .dataCriacao(entity.getDataCriacao())
                    .faseAtual(FaseMapper.paraDomain(entity.getFaseAtual()))
                    .primeiraNavegacao(entity.getPrimeiraNavegacao())
                    .build();
        } else {
            return MovimentacaoNegocio.builder()
                    .id(entity.getId())
                    .negocio(NegocioMapper.paraDomain(entity.getNegocio()))
                    .dataCriacao(entity.getDataCriacao())
                    .faseAntiga(FaseMapper.paraDomain(entity.getFaseAntiga()))
                    .faseAtual(FaseMapper.paraDomain(entity.getFaseAtual()))
                    .primeiraNavegacao(entity.getPrimeiraNavegacao())
                    .build();
        }

    }

    public static MovimentacaoNegocioEntity paraEntity(MovimentacaoNegocio domain) {

        if(domain.getFaseAntiga() == null) {
            return MovimentacaoNegocioEntity.builder()
                    .id(domain.getId())
                    .negocio(NegocioMapper.paraEntity(domain.getNegocio()))
                    .dataCriacao(domain.getDataCriacao())
                    .faseAtual(FaseMapper.paraEntity(domain.getFaseAtual()))
                    .primeiraNavegacao(domain.getPrimeiraNavegacao())
                    .build();
        } else {
            return MovimentacaoNegocioEntity.builder()
                    .id(domain.getId())
                    .negocio(NegocioMapper.paraEntity(domain.getNegocio()))
                    .dataCriacao(domain.getDataCriacao())
                    .faseAntiga(FaseMapper.paraEntity(domain.getFaseAntiga()))
                    .faseAtual(FaseMapper.paraEntity(domain.getFaseAtual()))
                    .primeiraNavegacao(domain.getPrimeiraNavegacao())
                    .build();
        }
    }

    public static MovimentacaoNegocio paraDomainDeDto(MovimentacaoNegociosDto dto) {
        if(dto.getOldStage() == null) {
            return MovimentacaoNegocio.builder()
                    .id(dto.getId())
                    .negocio(NegocioMapper.paraDomainDeDto(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAtual(FaseMapper.paraDomainDeDto(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        } else {
            return MovimentacaoNegocio.builder()
                    .id(dto.getId())
                    .negocio(NegocioMapper.paraDomainDeDto(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAntiga(FaseMapper.paraDomainDeDto(dto.getOldStage()))
                    .faseAtual(FaseMapper.paraDomainDeDto(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        }


    }

}
