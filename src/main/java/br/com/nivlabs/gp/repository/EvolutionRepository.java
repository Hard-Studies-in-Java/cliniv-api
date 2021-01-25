package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Evolution;

/**
 * Repositório de manipulação de dados relacionais para evoluções clínicas dos pacientes
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, Long> {

}
