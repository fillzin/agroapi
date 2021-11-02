package br.com.agrostok.repository;

import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.entity.Ingrediente;
import br.com.agrostok.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>, JpaSpecificationExecutor<Ingrediente> {

	Page<Ingrediente> findAll(Pageable pageable);

	Optional<Ingrediente> findById(Long id);

	Optional<Ingrediente> findByName(String name);

}
