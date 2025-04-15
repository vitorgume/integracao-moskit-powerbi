package com.gumeinteligencia.integracao_moskit_powerbi.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaUseCase {

    private final UsuarioUseCase usuarioUseCase;
    private final NegocioUseCase negocioUseCase;
    private final FunilUseCase funilUseCase;
    private final FaseUseCase faseUseCase;
    private final EmpresaUseCase empresaUseCase;
    private final MovimentacaoUseCase movitacoesUseCase;
    private final ProdutoUseCase produtoUseCase;

    public String gatewayAtualizacoes() {
        int quantidadeExecucoesUsuario = usuarioUseCase.atualiza();
        int quantidadeExecucoesFunil = funilUseCase.atualiza();
        int quantidadeExecucoesFase = faseUseCase.atualiza();
        int quantidadeExecucoesEmpresa = empresaUseCase.atualiza();
        int quantidadeExecucoesProduto = produtoUseCase.atualiza();
        int quantidadeExecucoesNegocio = negocioUseCase.atualiza();
        int quantidadeExecucoesMovimentacoesNegocios = movitacoesUseCase.atualiza();

        int total = quantidadeExecucoesUsuario + quantidadeExecucoesFunil + quantidadeExecucoesFase
                + quantidadeExecucoesEmpresa + quantidadeExecucoesNegocio + quantidadeExecucoesMovimentacoesNegocios
                + quantidadeExecucoesProduto;

        return "Atualização concluida com sucesso, quantidade de operações: " + total;
    }
}
