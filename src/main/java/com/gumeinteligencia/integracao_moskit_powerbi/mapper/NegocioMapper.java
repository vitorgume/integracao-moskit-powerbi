package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Negocio;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Qualificacao;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.NegocioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.CampoPersonalizadoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;

public class NegocioMapper {

    public static Negocio paraDomain(NegocioEntity entity) {
        return Negocio.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stage(FaseMapper.paraDomain(entity.getStage()))
                .status(entity.getStatus())
                .responsible(UsuarioMapper.paraDomain(entity.getResponsible()))
                .createdBy(UsuarioMapper.paraDomain(entity.getCreatedBy()))
                .dateCreated(entity.getDateCreated())
                .previsionCloseDate(entity.getPrevisionCloseDate())
                .closeDate(entity.getCloseDate())
                .qualificacao(entity.getQualificacao())
                .build();
    }

    public static NegocioEntity paraEntity(Negocio domain) {
        return NegocioEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice())
                .stage(FaseMapper.paraEntity(domain.getStage()))
                .status(domain.getStatus())
                .responsible(UsuarioMapper.paraEntity(domain.getResponsible()))
                .createdBy(UsuarioMapper.paraEntity(domain.getCreatedBy()))
                .dateCreated(domain.getDateCreated())
                .previsionCloseDate(domain.getPrevisionCloseDate())
                .closeDate(domain.getCloseDate())
                .qualificacao(domain.getQualificacao())
                .build();
    }

    public static Negocio paraDomainDeDto(NegocioDto dto) {
        return Negocio.builder()
                .id(dto.id())
                .name(dto.name())
                .price(dto.price())
                .stage(FaseMapper.paraDomainDeDto(dto.stage()))
                .status(dto.status())
                .responsible(UsuarioMapper.paraDomainDeDto(dto.responsible()))
                .createdBy(UsuarioMapper.paraDomainDeDto(dto.createdBy()))
                .dateCreated(trasnformaData(dto.dateCreated()))
                .previsionCloseDate(trasnformaData(dto.previsionCloseDate()))
                .closeDate(trasnformaData(dto.closeDate()))
                .qualificacao(organizaQualificacao(dto))
                .build();
    }

    private static LocalDate trasnformaData(String data) {
        if(data != null) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(data);
            LocalDate dataTransformada = offsetDateTime.toLocalDate();
            return dataTransformada;
        } else {
            return null;
        }
    }

    private static Qualificacao organizaQualificacao(NegocioDto dto) {
        Optional<CampoPersonalizadoDto> campoPersonalizadoOptional = dto
                .entityCustomFields()
                .stream()
                .filter(campoPersonalizadoDto -> campoPersonalizadoDto.id().equals("CF_8P5q4Vi6ioJ7lmRJ"))
                .findFirst();

        Qualificacao qualificacao = null;

        if(campoPersonalizadoOptional.isPresent()) {
            CampoPersonalizadoDto campoPersonalizado = campoPersonalizadoOptional.get();
            Integer codigoOption = campoPersonalizado.options().get(0);

            qualificacao = Arrays.stream(Qualificacao.values())
                    .filter(q -> q.getCodigo().equals(codigoOption))
                    .findFirst()
                    .orElse(null);

            if(qualificacao == null) {
                throw new RuntimeException("Qualificação com não encontrada com id: " + codigoOption);
            }
        }

        return qualificacao;
    }
}