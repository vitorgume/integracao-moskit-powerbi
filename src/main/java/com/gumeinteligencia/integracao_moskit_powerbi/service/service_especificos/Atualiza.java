package com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos;

import java.util.List;

public interface Atualiza<D> {
    int atualiza();
    List<D> consultaApi();
}
