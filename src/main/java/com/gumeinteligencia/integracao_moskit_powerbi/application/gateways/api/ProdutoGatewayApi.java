package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.ProdutoDto;

import java.util.List;

public interface ProdutoGatewayApi {
    List<ProdutoDto> consultarProdutos();
}
