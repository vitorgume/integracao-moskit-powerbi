package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.MotivoPerdaUseCase;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.CampoPersonalizadoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MotivoPerdaDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class NegocioMapper {

    private final MotivoPerdaUseCase motivoPerdaUseCase;
    private final FaseMapper faseMapper;
    private final UsuarioMapper usuarioMapper;
    private final MapperEnum mapperEnum;
    private final ProdutoNegocioMapper produtoNegocioMapper;

    public Negocio paraDomain(NegocioDto dto) {
        return Negocio.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stage(faseMapper.paraDomain(dto.getStage()))
                .status(dto.getStatus())
                .responsible(usuarioMapper.paraDomain(dto.getResponsible()))
                .createdBy(usuarioMapper.paraDomain(dto.getCreatedBy()))
                .dateCreated(MapperData.trasnformaData(dto.getDateCreated()))
                .closeDate(MapperData.trasnformaData(dto.getCloseDate()))
                .motivoPerda(this.mapperMotivoPerda(dto))
                .segmento(mapperEnum.organizaSegmento(dto))
                .dealProducts(produtoNegocioMapper.paraDomain(dto.getDealProducts()))
                .build();
    }

    public NegocioDto paraDto(Negocio domain) {
        if(domain.getCloseDate() == null) {
            return NegocioDto.builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .price(domain.getPrice())
                    .stage(faseMapper.paraDto(domain.getStage()))
                    .status(domain.getStatus())
                    .responsible(usuarioMapper.paraDto(domain.getResponsible()))
                    .createdBy(usuarioMapper.paraDto(domain.getCreatedBy()))
                    .dateCreated(MapperData.trasformaDataString(domain.getDateCreated()))
                    .dealProducts(produtoNegocioMapper.paraDto(domain.getDealProducts()))
                    .build();
        } else {
            return NegocioDto.builder()
                    .id(domain.getId())
                    .name(domain.getName())
                    .price(domain.getPrice())
                    .stage(faseMapper.paraDto(domain.getStage()))
                    .status(domain.getStatus())
                    .responsible(usuarioMapper.paraDto(domain.getResponsible()))
                    .createdBy(usuarioMapper.paraDto(domain.getCreatedBy()))
                    .dateCreated(MapperData.trasformaDataString(domain.getDateCreated()))
                    .closeDate(MapperData.trasformaDataString(domain.getCloseDate()))
                    .dealProducts(produtoNegocioMapper.paraDto(domain.getDealProducts()))
                    .build();
        }
    }

    private String mapperMotivoPerda(NegocioDto dto) {

        if(dto.getLostReason() == null) {
            return "SEM_MOTIVO";
        }

        MotivoPerdaDto motivoPerda = motivoPerdaUseCase.consultarMotivo(dto.getLostReason().getId());

        return motivoPerda.getName();
    }
}