package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException() {
        super("Produto não encontrdo pelo seu id.");
    }
}
