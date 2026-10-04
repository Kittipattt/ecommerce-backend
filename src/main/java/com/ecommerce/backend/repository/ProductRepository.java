package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByFeaturedTrue();

    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Product> findFilteredProducts(@Param("categoryId") Long categoryId,
                                      @Param("search") String search,
                                      Pageable pageable);

    long countByStockQuantityLessThanEqual(int threshold);

    List<Product> findTop5ByOrderByStockQuantityAsc();
    List<Product> findTop6ByOrderByRatingDesc();
}
