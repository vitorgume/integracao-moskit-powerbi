package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.bd;

import com.gumeinteligencia.integracao_moskit_powerbi.domain.Usuario;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.dataprovider.exceptions.DataProviderException;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.mapper.UsuarioMapper;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.UsuarioEntity;
import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioDataProvider {

    private final UsuarioRepository repository;
    private final String MENSAGEM_ERRO_LISTAGEM_USUARIOS = "Erro ao listar usuários.";
    private final String MENSAGEM_ERRO_SALVAR_USUARIO = "Erro ao salvar usuário.";
    private final String MENSAGEM_ERRO_CONSULTAR_USUARIO_POR_ID = "Erro ao consultar usuário por id.";

    public List<Usuario> listarUsuarios() {
        List<UsuarioEntity> usuarioEntities;

        try {
            usuarioEntities = repository.findAll();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_LISTAGEM_USUARIOS, ex);
            throw new DataProviderException(MENSAGEM_ERRO_LISTAGEM_USUARIOS, ex.getCause());
        }

        return usuarioEntities.stream().map(UsuarioMapper::paraDomain).toList();
    }

    public Usuario salvar(Usuario usuario) {
        UsuarioEntity usuarioSalvo;

        try {
            usuarioSalvo = repository.save(UsuarioMapper.paraEntity(usuario));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_USUARIO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_USUARIO, ex.getCause());
        }

        return UsuarioMapper.paraDomain(usuarioSalvo);
    }

    public Optional<Usuario> consultarPorId(Integer id) {
        Optional<UsuarioEntity> usuarioEntity;

        try {
            usuarioEntity = repository.findById(id);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_USUARIO_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_USUARIO_POR_ID, ex.getCause());
        }

        return usuarioEntity.map(UsuarioMapper::paraDomain);
    }
}
