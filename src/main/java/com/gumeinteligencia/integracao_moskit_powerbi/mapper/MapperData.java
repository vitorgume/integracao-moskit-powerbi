package com.gumeinteligencia.integracao_moskit_powerbi.mapper;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class MapperData {
    public static LocalDate trasnformaData(String data) {
        if (data != null) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(data);
            LocalDate dataTransformada = offsetDateTime.toLocalDate();
            return dataTransformada;
        } else {
            return null;
        }
    }
}
