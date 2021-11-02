package br.com.agrostok.repository;

import br.com.agrostok.entity.ProductIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductIngredientRepository extends JpaRepository<ProductIngrediente, Long>, JpaSpecificationExecutor<ProductIngrediente> {

    @Query("select i from ProductIngrediente i where i.product.id = :productId")
    List<ProductIngrediente> findByProductId(Long productId);
}
