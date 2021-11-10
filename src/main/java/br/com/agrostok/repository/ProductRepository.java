package br.com.agrostok.repository;

import java.util.List;
import java.util.Optional;

import br.com.agrostok.entity.SaleProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query(value="SELECT x FROM Product x WHERE x.userCreatedId =:userId")
	Page<Product> findByFiltros(Long userId, Pageable pageable);
	
	Optional<Product> findById(Long id);
	
	Optional<Product> findByNameAndUserCreatedId(String name, Long userId);
	
	@Query(value="SELECT s "
			+ " FROM SaleProduct s INNER JOIN s.product p  "
			+ " WHERE s.userCreatedId =:userId"
			+ " GROUP BY s.product.id"
			+ " ORDER BY sum(s.total) desc")
	List<SaleProduct> findProductAndSaleValue(Long userId);
}
