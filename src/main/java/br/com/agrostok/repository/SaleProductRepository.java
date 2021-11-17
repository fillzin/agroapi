package br.com.agrostok.repository;

import br.com.agrostok.entity.Sale;
import br.com.agrostok.entity.SaleProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Long>, JpaSpecificationExecutor<SaleProduct> {


	List<SaleProduct> findBySaleId(Long saleId);


}
