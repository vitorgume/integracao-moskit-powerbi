package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {}
