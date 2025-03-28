package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.MotivoPerdaUseCase;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.MotivoPerdaDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NegocioMapper {

    private final MotivoPerdaUseCase motivoPerdaUseCase;
    private final FaseMapper faseMapper;
    private final UsuarioMapper usuarioMapper;

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
                .qualificacao(MapperEnum.organizaQualificacao(dto))
                .motivoPerda(this.mapperMotivoPerda(dto))
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
                    .qualificacao(domain.getQualificacao())
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
                    .qualificacao(domain.getQualificacao())
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