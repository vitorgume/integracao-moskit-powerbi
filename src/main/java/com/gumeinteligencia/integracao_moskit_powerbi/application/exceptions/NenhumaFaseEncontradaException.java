package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NenhumaFaseEncontradaException extends RuntimeException {
    public NenhumaFaseEncontradaException() {
        super("Nenhuma fase encontrado");
    }
}
