package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GeradorDeData {
    public static String gerarData() {
        LocalDate data = LocalDate.now().minusDays(1);

        OffsetDateTime dataFormatada = data.atStartOfDay().atOffset(ZoneOffset.UTC);

        return dataFormatada.format(DateTimeFormatter.ISO_INSTANT);
    }
}
