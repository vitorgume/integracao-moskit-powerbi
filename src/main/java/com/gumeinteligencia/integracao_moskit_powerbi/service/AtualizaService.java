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
        int quantidadeExecucoesUsuario = usuarioService.atualiza();
        int quantidadeExecucoesFunil = funilService.atualiza();
        int quantidadeExecucoesFase = faseService.atualiza();
        int quantidadeExecucoesEmpresa = empresaService.atualiza();
        int quantidadeExecucoesNegocio = negocioService.atualiza();

        int total = quantidadeExecucoesUsuario + quantidadeExecucoesFunil + quantidadeExecucoesFase
                + quantidadeExecucoesEmpresa + quantidadeExecucoesNegocio;

        return "Operação concluida com sucesso, quantidade de operações: " + total;
    }
}
