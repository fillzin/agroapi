package br.com.agrostok.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

	
	@Query(value="SELECT x FROM Sale x WHERE x.userCreatedId =:userId order by x.createdDate desc")
	Page<Sale> findByFiltros(Long userId, Pageable pageable);
	
	@Query(value="SELECT x FROM Sale x WHERE x.userCreatedId =:userId and "
			+ " createdDate >= :startDate and createdDate <= :endDate")
	List<Sale> findByYear(Long userId, LocalDateTime startDate, LocalDateTime endDate);

	@Query(value="SELECT sum(x.value) FROM Sale x")
	BigDecimal somaReceita();
}
