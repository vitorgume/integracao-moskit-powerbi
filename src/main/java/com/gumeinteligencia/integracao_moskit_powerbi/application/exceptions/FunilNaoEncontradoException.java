package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class FunilNaoEncontradoException extends RuntimeException {
    public FunilNaoEncontradoException(Integer id) {
        super("Fúnil não encontrado com id: " + id);
    }
}
