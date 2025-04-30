package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.ProdutoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoMapper {

    private final UsuarioMapper usuarioMapper;

    public Produto paraDomain(ProdutoDto dto) {
        return Produto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .active(dto.getActive())
                .price(dto.getPrice())
                .createdBy(usuarioMapper.paraDomain(dto.getCreatedBy()))
                .build();
    }

    public ProdutoDto paraDto(Produto dto) {
        return ProdutoDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .active(dto.getActive())
                .price(dto.getPrice())
                .createdBy(usuarioMapper.paraDto(dto.getCreatedBy()))
                .build();
    }
}
