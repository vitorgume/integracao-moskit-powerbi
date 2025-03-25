package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.api.exceptions;

public class DataProviderApiException extends RuntimeException {
    public DataProviderApiException(String mensagem) {
        super(mensagem);
    }
}
