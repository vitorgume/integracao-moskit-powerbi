package com.gumeinteligencia.integracao_moskit_powerbi.service;

import com.gumeinteligencia.integracao_moskit_powerbi.service.service_especificos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaService {

    private final AtualizaUsuarioService usuarioService;
    private final AtualizaNegocioService negocioService;
    private final AtualizaFunilService funilService;
    private final AtualizaFaseService faseService;
    private final AtualizaEmpresaService empresaService;

    public String gatewayAtualizacoes() {

    }
}
