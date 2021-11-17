package br.com.agrostok.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>, JpaSpecificationExecutor<Stock> {

	@Query(value = "SELECT x FROM Stock x JOIN x.ingrediente i  WHERE x.userCreatedId =:userId AND ((:name is null) or (i.name like %:name%)) ORDER BY x.count ASC")
	Page<Stock> findByFiltros(Long userId, Pageable pageable, String name);

	@Query(value = "SELECT x FROM Stock x WHERE x.userCreatedId =:userId AND x.product.id = :productId")
	Optional<Stock> findByProductAndUser(Long userId, Long productId);

	@Query(value = "SELECT sum(x.value) FROM Stock x")
	BigDecimal somaDespesa();
}
