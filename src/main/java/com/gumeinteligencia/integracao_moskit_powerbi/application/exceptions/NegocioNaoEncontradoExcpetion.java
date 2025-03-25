package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NegocioNaoEncontradoExcpetion extends RuntimeException {
    public NegocioNaoEncontradoExcpetion() {
        super("Negocio não encontrado.");
    }
}
