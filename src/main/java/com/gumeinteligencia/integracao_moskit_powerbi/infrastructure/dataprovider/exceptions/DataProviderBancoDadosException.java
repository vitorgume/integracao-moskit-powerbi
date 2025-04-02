package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions;

public class DataProviderBancoDadosException extends RuntimeException {
    public DataProviderBancoDadosException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
