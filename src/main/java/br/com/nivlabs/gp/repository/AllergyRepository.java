package br.com.nivlabs.gp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Allergy;
import br.com.nivlabs.gp.repository.custom.allergy.AllergyRepositoryCustom;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long>, AllergyRepositoryCustom {

    public Optional<Allergy> findByDescriptionIgnoreCase(String description);

}
