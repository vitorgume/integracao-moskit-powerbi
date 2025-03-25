package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions;

public class DataProviderException extends RuntimeException {
    public DataProviderException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
