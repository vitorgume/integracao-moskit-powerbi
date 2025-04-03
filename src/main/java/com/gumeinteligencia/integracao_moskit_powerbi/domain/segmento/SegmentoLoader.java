package com.gumeinteligencia.integracao_moskit_powerbi.domain.segmento;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SegmentoLoader {

    private final SegmentoProperties segmentoProperties;

    @PostConstruct
    public void init() {
        Segmento.carregarCodigos(segmentoProperties);
    }
}
