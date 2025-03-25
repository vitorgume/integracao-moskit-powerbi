package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NenhumFunilEncontradoException extends RuntimeException {
    public NenhumFunilEncontradoException() {
        super("Nenhuma movimentação encontrada");
    }
}
