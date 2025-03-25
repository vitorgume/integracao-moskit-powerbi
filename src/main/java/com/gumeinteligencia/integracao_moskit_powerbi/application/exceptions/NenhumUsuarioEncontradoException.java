package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NenhumUsuarioEncontradoException extends RuntimeException {
    public NenhumUsuarioEncontradoException() {
        super("Nenhum usuário encontrado.");
    }
}
