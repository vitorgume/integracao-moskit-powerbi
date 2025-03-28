package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Movimentacao;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MovimentacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MovimentacaoMapper {

    private final NegocioMapper negocioMapper;
    private final FaseMapper faseMapper;

    public Movimentacao paraDomain(MovimentacaoDto dto) {
        if(dto.getOldStage() == null) {
            return Movimentacao.builder()
                    .id(dto.getId())
                    .negocio(negocioMapper.paraDomain(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAtual(faseMapper.paraDomain(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        } else {
            return Movimentacao.builder()
                    .id(dto.getId())
                    .negocio(negocioMapper.paraDomain(dto.getDeal()))
                    .dataCriacao(MapperData.trasnformaData(dto.getDateCreated()))
                    .faseAntiga(faseMapper.paraDomain(dto.getOldStage()))
                    .faseAtual(faseMapper.paraDomain(dto.getCurrentStage()))
                    .primeiraNavegacao(dto.getFirstNavigation())
                    .build();
        }


    }

}
