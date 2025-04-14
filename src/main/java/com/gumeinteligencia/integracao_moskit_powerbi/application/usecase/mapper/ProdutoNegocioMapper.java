package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.ProdutoUseCase;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.ProdutoNegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoNegocioMapper {

    private final ProdutoMapper produtoMapper;
    private final ProdutoUseCase produtoUseCase;

    public List<Produto> paraDomain(List<ProdutoNegocioDto> produtoNegocioDtos) {

        if(produtoNegocioDtos == null) {
            return new ArrayList<>();
        }

        return produtoNegocioDtos.stream()
                .map(produtoNegocio ->
                        produtoUseCase.consultarPorId(produtoNegocio.getProduct().getId()))
                .toList();
    }

    public List<ProdutoNegocioDto> paraDto(List<Produto> produtosDomain) {
        return produtosDomain.stream()
                .map(produto -> ProdutoNegocioDto.builder()
                        .quantity(0)
                        .price(BigDecimal.ZERO)
                        .finalPrice(BigDecimal.ZERO)
                        .initialPrice(BigDecimal.ZERO)
                        .product(produtoMapper.paraDto(produto))
                        .build())
                .toList();
    }
}
