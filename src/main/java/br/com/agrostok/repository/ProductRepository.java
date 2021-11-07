package br.com.agrostok.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query(value="SELECT x FROM Product x WHERE x.userCreatedId =:userId AND ((:name is null) or (x.name like %:name%))")
	Page<Product> findByFiltros(Long userId, Pageable pageable, String name);
	
	Optional<Product> findById(Long id);
	
	Optional<Product> findByNameAndUserCreatedId(String name, Long userId);
	
	@Query(value="SELECT new br.com.agrostok.dto.SaledProductDto( sum(s.total), p.name ) "
			+ " FROM SaleProduct s INNER JOIN s.product p  "
			+ " WHERE s.userCreatedId =:userId"
			+ " GROUP BY s.product.id"
			+ " ORDER BY sum(s.total) desc")
	List<SaledProductDto> findProductAndSaleValue(Long userId);
	
	@Query(value="SELECT x FROM Product x WHERE x.name like %?1%")
	Page<Product> findByName(String name, Pageable pageable);
}
