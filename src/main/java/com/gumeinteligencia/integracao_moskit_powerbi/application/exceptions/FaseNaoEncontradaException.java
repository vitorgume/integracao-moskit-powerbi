package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class FaseNaoEncontradaException extends RuntimeException {
    public FaseNaoEncontradaException(Integer id) {
        super("Fase não encontrada com o id: " + id);
    }
}
