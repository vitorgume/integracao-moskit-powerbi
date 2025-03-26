package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MovimentacaoDto;

public class MovimentacaoNegocioMapper {

    public static Movimentacao paraDomain(MovimentacaoDto dto) {
        if(dto.getOldStage() == null) {
            return Movimentacao.builder()
                    .id(dto.getId())
                    .negocio(NegocioMapper.paraDomain(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAtual(FaseMapper.paraDomain(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        } else {
            return Movimentacao.builder()
                    .id(dto.getId())
                    .negocio(NegocioMapper.paraDomain(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAntiga(FaseMapper.paraDomain(dto.getOldStage()))
                    .faseAtual(FaseMapper.paraDomain(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        }


    }

}
