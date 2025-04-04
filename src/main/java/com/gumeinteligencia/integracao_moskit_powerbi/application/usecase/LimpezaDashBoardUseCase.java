package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.MovimentacaoDashBoardGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.NegocioDashBoardGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LimpezaDashBoardUseCase {

    private final NegocioDashBoardGateway negocioDashBoardGateway;
    private final MovimentacaoDashBoardGateway movimentacaoDashBoardGateway;

    @Scheduled(cron = "0 5 0 1 * ?")
    public void limparTabelasDashboard() {
        log.info("Iniciadno limpeza das tabelas do dashboard...");

        negocioDashBoardGateway.limpar();
        movimentacaoDashBoardGateway.limpar();

        log.info("Limpeza das tabelas do dashboard concluida com sucesso.");
    }
}
