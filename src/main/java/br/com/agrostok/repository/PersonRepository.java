package br.com.agrostok.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

	@Query(value = "SELECT x FROM Person x WHERE x.userCreatedId =:userId AND x.type =:type")
	Page<Person> findByFiltros(Long userId, Integer type, Pageable pageable);

	Optional<Person> findById(Long id);

	Optional<Person> findByNameAndUserCreatedId(String name, Long userId);
}
