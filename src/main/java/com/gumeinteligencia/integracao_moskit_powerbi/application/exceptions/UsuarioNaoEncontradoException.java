package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Integer id) {
        super("Usuário não encontrado com id: " + id);
    }
}
