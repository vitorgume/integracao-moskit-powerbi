package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class EmpresaNaoEncontradaException extends RuntimeException {
    public EmpresaNaoEncontradaException(Integer id) {
        super("Empresa não encontrada com o id: " + id);
    }
}
