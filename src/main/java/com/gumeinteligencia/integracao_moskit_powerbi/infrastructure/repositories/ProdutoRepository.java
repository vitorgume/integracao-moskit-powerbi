package com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories;

import com.gumeinteligencia.integracao_moskit_powerbi.infrastructure.repositories.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
}
