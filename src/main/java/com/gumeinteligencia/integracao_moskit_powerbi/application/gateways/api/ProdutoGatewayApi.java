package com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.api;

import com.gumeinteligencia.integracao_moskit_powerbi.application.usecase.dto.ProdutoDto;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Produto;

import java.util.Arrays;
import java.util.List;

public interface ProdutoGatewayApi {
    List<ProdutoDto> consultarProdutos();
}
