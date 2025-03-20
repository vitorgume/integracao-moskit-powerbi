package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtividadeNegocioDataProvider {

    private final AtividadesNegocioRepository repository;



}
