package br.com.agrostok.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.agrostok.entity.Stock;
import br.com.agrostok.entity.StockHist;

public interface StockHistRepository extends JpaRepository<StockHist, Long>, JpaSpecificationExecutor<StockHist> {

	@Query(value = "SELECT x FROM StockHist x JOIN x.stock s JOIN s.ingrediente i WHERE x.userCreatedId =:userId AND ((:name is null) or (i.name like %:name%)) ORDER BY 1 DESC")
	Page<StockHist> findByFiltros(Long userId, Pageable pageable, String name);
	
	

}
