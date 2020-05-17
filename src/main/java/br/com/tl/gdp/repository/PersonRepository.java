package br.com.tl.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tl.gdp.models.domain.Person;

/**
 * 
 * Classe PersonRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    /**
     * @param cpf
     * @return
     */
    public Optional<Person> findByCpf(String cpf);

    /**
     * @param name
     * @param motherName
     * @param bornDate
     * @return
     */
    @Query("from Person where firstName like :name and motherName like :motherName")
    public List<Person> findByComposition(String name, String motherName);

    /**
     * @param userApplication
     * @return
     */
    @Query("from Person where user.id = :id")
    public Person findByUser(Long id);

}
