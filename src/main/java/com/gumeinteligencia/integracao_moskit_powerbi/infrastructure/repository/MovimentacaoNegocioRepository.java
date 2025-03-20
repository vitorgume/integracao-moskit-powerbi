package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repository;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.entity.MovimentacaoNegocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoNegocioRepository extends JpaRepository<MovimentacaoNegocioEntity, Integer> {
}
