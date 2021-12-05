package br.com.agrostok.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.agrostok.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.dto.ReturnSaleDto;
import br.com.agrostok.dto.SaleDto;
import br.com.agrostok.entity.Sale;
import br.com.agrostok.entity.SaleProduct;

public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

	
	@Query(value="SELECT x FROM Sale x WHERE x.userCreatedId =:userId order by x.createdDate desc")
	Page<Sale> findByFiltros(Long userId, Pageable pageable);
	
	@Query(value="SELECT x FROM Sale x WHERE x.userCreatedId =:userId and "
			+ " createdDate >= :startDate and createdDate <= :endDate")
	List<Sale> findByYear(Long userId, LocalDateTime startDate, LocalDateTime endDate);

	@Query(value="SELECT sum(x.value) FROM Sale x")
	BigDecimal 	somaReceita();
	
	@Query("SELECT new br.com.agrostok.dto.SaleDto(s.condominio, s.bloco, s.casa, count(*) as qtd)  "+
			"FROM Sale s GROUP BY s.condominio, s.bloco, s.casa ORDER BY qtd DESC")
	Page<SaleDto> orderByClient(Pageable pageable);


	@Query("SELECT SUM(s.value) from Stock s  where s.ingrediente.id in (:ids) and s.createdDate >= :startDate and ((:endDate is null) or s.createdDate <= :endDate)")
	BigDecimal findTotalDespesa(List<Long> ids, LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT SUM(sp.total) from Sale s join s.saleProducts sp join sp.product p  where p.id in (:ids) and s.createdDate >= :startDate and ((:endDate is null) or s.createdDate <= :endDate)")
	BigDecimal findTotalVendido(List<Long> ids, LocalDateTime startDate, LocalDateTime endDate);
	@Query("SELECT SUM(sp.custo) from  Sale s join s.saleProducts sp  join sp.product p where p.id = :id and s.createdDate >= :startDate and ((:endDate is null) or s.createdDate <= :endDate)")
	BigDecimal findTotalDespesaCustoFixo(Long id, LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT p from  Sale s join s.saleProducts sp  join sp.product p where s.createdDate >= :startDate and ((:endDate is null) or s.createdDate <= :endDate) group by p.id")
	List<Product> findProductsSalesByMonth(LocalDateTime startDate, LocalDateTime endDate);
	
	
	@Query("SELECT new br.com.agrostok.dto.ReturnSaleDto(s.product.id, p.name, s.total, count(s.product.id) as soma) FROM SaleProduct s LEFT JOIN s.product p  group by s.product.id having count(s.product.id) >=0 order by soma DESC")
	List<ReturnSaleDto> findProductsByNameAndHighestValue();
	

}
