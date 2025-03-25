package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NenhumNegocioEncontradoException extends RuntimeException {
    public NenhumNegocioEncontradoException() {
        super("Nenhum negócio encontrado");
    }
}
