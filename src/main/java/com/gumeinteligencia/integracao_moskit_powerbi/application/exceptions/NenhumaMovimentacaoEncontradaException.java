package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class NenhumaMovimentacaoEncontradaException extends RuntimeException {
    public NenhumaMovimentacaoEncontradaException() {
        super("Nenhuma movimentação encontrada");
    }
}
