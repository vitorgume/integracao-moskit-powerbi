package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.FunilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunilRepository extends JpaRepository<FunilEntity, Integer> {
}
