package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.segmento.Segmento;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.CampoPersonalizadoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.NegocioDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class MapperEnum {

    @Value("${brightdash.campo_personalizado.cod.filtro}")
    private final String codCampoPersonalizadoFiltro;

    public MapperEnum(
            @Value("${brightdash.campo_personalizado.cod.filtro}") String codCampoPersonalizadoFiltro
    ) {
        this.codCampoPersonalizadoFiltro = codCampoPersonalizadoFiltro;
    }

    public Segmento organizaSegmento(NegocioDto dto) {

        if (dto.getEntityCustomFields() != null) {
            if (!dto.getEntityCustomFields().isEmpty()) {

                Optional<CampoPersonalizadoDto> campoPersonalizadoOptional = dto.getEntityCustomFields()
                        .stream()
                        .filter(campoPersonalizado -> campoPersonalizado.id().equals(codCampoPersonalizadoFiltro))
                        .findFirst();

                if (campoPersonalizadoOptional.isPresent()) {
                    CampoPersonalizadoDto campoPersonalizado = campoPersonalizadoOptional.get();

                    if (campoPersonalizado.options() != null) {
                        Integer codigoOption = campoPersonalizado.options().get(0);

                        Segmento segmento;

                        segmento = Arrays.stream(Segmento.values())
                                .filter(q -> q.getCodigo().equals(codigoOption))
                                .findFirst()
                                .orElse(null);

                        if (segmento != null) {
                            return segmento;
                        }

                    }
                }
            }
        }

        return Segmento.SEM_SEGMENTO;
    }
}
