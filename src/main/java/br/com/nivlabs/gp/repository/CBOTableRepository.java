package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.CBOTable;
import br.com.nivlabs.gp.repository.custom.cbo.CBOTableRepositoryCustom;

/**
 * Repositório para Classificação Brasileira de Ocupação
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface CBOTableRepository extends JpaRepository<CBOTable, Long>, CBOTableRepositoryCustom {

}
