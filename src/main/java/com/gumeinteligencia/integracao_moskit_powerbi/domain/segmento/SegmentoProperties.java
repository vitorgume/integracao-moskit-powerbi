package com.gumeinteligencia.integracao_moskit_powerbi.domain.segmento;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
@Setter
@ConfigurationProperties(prefix = "brightdash.segmento")
public class SegmentoProperties {

    private Map<String, Integer> cod;

    public Integer getCodigo(String key) {
        return cod.getOrDefault(key, 0);
    }
}
