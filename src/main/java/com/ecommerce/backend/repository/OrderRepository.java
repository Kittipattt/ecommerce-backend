package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.OrderStatus;
import com.ecommerce.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Optional<Order> findByOrderNumber(String orderNumber);
    long countByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status != 'CANCELLED'")
    BigDecimal calculateTotalRevenue();

    List<Order> findTop5ByOrderByCreatedAtDesc();
}
