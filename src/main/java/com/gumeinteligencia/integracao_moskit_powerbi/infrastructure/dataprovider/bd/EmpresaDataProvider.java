package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.application.gateways.bd.EmpresaGateway;
import com.gumeinteligencia.integracao_moskit_powerbi.domain.Empresa;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderBancoDadosException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.EmpresaMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.EmpresaRepository;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.EmpresaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmpresaDataProvider implements EmpresaGateway {

    private final EmpresaRepository repository;
    private final String MENSAGEM_ERRO_LISTAGEM_EMPRESAS = "Erro ao listar empresas.";
    private final String MENSAGEM_ERRO_SALVAR_EMPRESA = "Erro ao salvar empresa.";
    private final String MENSAGEM_ERRO_CONSULTAR_EMPRESA_POR_ID = "Erro ao consultar empresa pelo id.";

    @Override
    public List<Empresa> listar() {
        List<EmpresaEntity> empresaEntities;

        try {
            empresaEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAGEM_EMPRESAS, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_LISTAGEM_EMPRESAS, ex.getCause());
        }

        return empresaEntities.stream().map(EmpresaMapper::paraDomain).toList();
    }

    @Override
    public Empresa salvar(Empresa empresa) {
        EmpresaEntity empresaSalva;

        try {
            empresaSalva = repository.save(EmpresaMapper.paraEntity(empresa));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_EMPRESA, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_SALVAR_EMPRESA, ex.getCause());
        }

        return EmpresaMapper.paraDomain(empresaSalva);
    }

    @Override
    public Optional<Empresa> consultarPorId(Integer id) {
        Optional<EmpresaEntity> empresa;

        try {
            empresa = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_EMPRESA_POR_ID, ex);
            throw new DataProviderBancoDadosException(MENSAGEM_ERRO_CONSULTAR_EMPRESA_POR_ID, ex.getCause());
        }

        return empresa.map(EmpresaMapper::paraDomain);
    }
}
