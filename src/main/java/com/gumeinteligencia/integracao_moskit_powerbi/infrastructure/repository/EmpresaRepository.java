package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {
}
