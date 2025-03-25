package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NenhumaEmpresaEncontradaException extends RuntimeException {
    public NenhumaEmpresaEncontradaException() {
        super("Nenhuma empresa encontrado");
    }
}
