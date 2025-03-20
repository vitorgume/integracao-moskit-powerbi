package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Qualificacao;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.TipoAtividade;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.AtividadeNegocioDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.CampoPersonalizadoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.dto.NegocioDto;

import java.util.Arrays;

public class MapperEnum {
    public static Qualificacao organizaQualificacao(NegocioDto dto) {

        if (dto.getEntityCustomFields() == null) {
            return Qualificacao.SEM_QUALIFICACAO;
        } else {
            if (!dto.getEntityCustomFields().isEmpty()) {
                CampoPersonalizadoDto campoPersonalizado = dto.getEntityCustomFields().get(0);

                if (campoPersonalizado.options() != null) {
                    Integer codigoOption = campoPersonalizado.options().get(0);

                    Qualificacao qualificacao;

                    qualificacao = Arrays.stream(Qualificacao.values())
                            .filter(q -> q.getCodigo().equals(codigoOption))
                            .findFirst()
                            .orElse(null);

                    if (qualificacao == null) {
                        return Qualificacao.SEM_QUALIFICACAO;
                    }

                    return qualificacao;
                } else {
                    return Qualificacao.SEM_QUALIFICACAO;
                }
            } else {
                return Qualificacao.SEM_QUALIFICACAO;
            }
        }
    }

    public TipoAtividade organizaTipoAtividade(AtividadeNegocioDto dto) {
        if (dto.getType() == null) {
            return TipoAtividade.SEM_QUALIFICACAO;
        } else {
            Integer codigo = dto.getType().getCodigo();

            TipoAtividade tipoAtividade;

            tipoAtividade = Arrays.stream(TipoAtividade.values())
                    .filter(q -> q.getCodigo().equals(codigo))
                    .findFirst()
                    .orElse(null);

            return tipoAtividade;
        }
    }
}
