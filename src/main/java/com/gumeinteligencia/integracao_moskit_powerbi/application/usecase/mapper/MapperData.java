package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
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

    public static String trasformaDataString(LocalDate data) {
        OffsetDateTime dateTime = data.atTime(13, 30, 17)
                .atOffset(ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return dateTime.format(formatter);
    }
}
