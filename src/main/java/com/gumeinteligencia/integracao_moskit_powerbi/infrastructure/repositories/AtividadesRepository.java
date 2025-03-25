package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.AtividadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadesRepository extends JpaRepository<AtividadeEntity, Integer> {
}
