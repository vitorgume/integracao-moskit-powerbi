package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.MovimentacoesNegocios;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.MovimentacoesNegociosEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.MovimentacoesNegociosDto;

public class MovimentacaoNegociosMapper {

    public static MovimentacoesNegocios paraDomain(MovimentacoesNegociosEntity entity) {

        if(entity.getFaseAntiga() == null) {
            return MovimentacoesNegocios.builder()
                    .id(entity.getId())
                    .negocio(NegocioMapper.paraDomain(entity.getNegocio()))
                    .dataCriacao(entity.getDataCriacao())
                    .faseAtual(FaseMapper.paraDomain(entity.getFaseAtual()))
                    .primeiraNavegacao(entity.getPrimeiraNavegacao())
                    .build();
        } else {
            return MovimentacoesNegocios.builder()
                    .id(entity.getId())
                    .negocio(NegocioMapper.paraDomain(entity.getNegocio()))
                    .dataCriacao(entity.getDataCriacao())
                    .faseAntiga(FaseMapper.paraDomain(entity.getFaseAntiga()))
                    .faseAtual(FaseMapper.paraDomain(entity.getFaseAtual()))
                    .primeiraNavegacao(entity.getPrimeiraNavegacao())
                    .build();
        }

    }

    public static MovimentacoesNegociosEntity paraEntity(MovimentacoesNegocios domain) {

        if(domain.getFaseAntiga() == null) {
            return MovimentacoesNegociosEntity.builder()
                    .id(domain.getId())
                    .negocio(NegocioMapper.paraEntity(domain.getNegocio()))
                    .dataCriacao(domain.getDataCriacao())
                    .faseAtual(FaseMapper.paraEntity(domain.getFaseAtual()))
                    .primeiraNavegacao(domain.getPrimeiraNavegacao())
                    .build();
        } else {
            return MovimentacoesNegociosEntity.builder()
                    .id(domain.getId())
                    .negocio(NegocioMapper.paraEntity(domain.getNegocio()))
                    .dataCriacao(domain.getDataCriacao())
                    .faseAntiga(FaseMapper.paraEntity(domain.getFaseAntiga()))
                    .faseAtual(FaseMapper.paraEntity(domain.getFaseAtual()))
                    .primeiraNavegacao(domain.getPrimeiraNavegacao())
                    .build();
        }
    }

    public static MovimentacoesNegocios paraDomainDeDto(MovimentacoesNegociosDto dto) {
        if(dto.getOldStage() == null) {
            return MovimentacoesNegocios.builder()
                    .id(dto.getId())
                    .negocio(NegocioMapper.paraDomainDeDto(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAtual(FaseMapper.paraDomainDeDto(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        } else {
            return MovimentacoesNegocios.builder()
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
