package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto;

import java.util.List;

public record CampoPersonalizadoDto(String id, String numericValue,
                                    String dateValue, String textValue, List<Integer> options) {}