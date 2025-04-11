package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.ProdutoEntity;

public class ProdutoMapper {

    public static Produto paraDomain(ProdutoEntity entity) {
        return Produto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .price(entity.getPrice())
                .createdBy(UsuarioMapper.paraDomain(entity.getCreatedBy()))
                .build();
    }

    public static ProdutoEntity paraEntity(Produto domain) {
        return ProdutoEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.getActive())
                .price(domain.getPrice())
                .createdBy(UsuarioMapper.paraEntity(domain.getCreatedBy()))
                .build();
    }
}
