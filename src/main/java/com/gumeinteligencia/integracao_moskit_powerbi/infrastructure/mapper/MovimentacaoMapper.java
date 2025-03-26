package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.MovimentacaoEntity;

public class MovimentacaoMapper {

    public static Movimentacao paraDomain(MovimentacaoEntity entity) {

        if(entity.getFaseAntiga() == null) {
            return Movimentacao.builder()
                    .id(entity.getId())
                    .negocio(NegocioMapper.paraDomain(entity.getNegocio()))
                    .dataCriacao(entity.getDataCriacao())
                    .faseAtual(FaseMapper.paraDomain(entity.getFaseAtual()))
                    .primeiraNavegacao(entity.getPrimeiraNavegacao())
                    .build();
        } else {
            return Movimentacao.builder()
                    .id(entity.getId())
                    .negocio(NegocioMapper.paraDomain(entity.getNegocio()))
                    .dataCriacao(entity.getDataCriacao())
                    .faseAntiga(FaseMapper.paraDomain(entity.getFaseAntiga()))
                    .faseAtual(FaseMapper.paraDomain(entity.getFaseAtual()))
                    .primeiraNavegacao(entity.getPrimeiraNavegacao())
                    .build();
        }

    }

    public static MovimentacaoEntity paraEntity(Movimentacao domain) {

        if(domain.getFaseAntiga() == null) {
            return MovimentacaoEntity.builder()
                    .id(domain.getId())
                    .negocio(NegocioMapper.paraEntity(domain.getNegocio()))
                    .dataCriacao(domain.getDataCriacao())
                    .faseAtual(FaseMapper.paraEntity(domain.getFaseAtual()))
                    .primeiraNavegacao(domain.getPrimeiraNavegacao())
                    .build();
        } else {
            return MovimentacaoEntity.builder()
                    .id(domain.getId())
                    .negocio(NegocioMapper.paraEntity(domain.getNegocio()))
                    .dataCriacao(domain.getDataCriacao())
                    .faseAntiga(FaseMapper.paraEntity(domain.getFaseAntiga()))
                    .faseAtual(FaseMapper.paraEntity(domain.getFaseAtual()))
                    .primeiraNavegacao(domain.getPrimeiraNavegacao())
                    .build();
        }
    }

}
