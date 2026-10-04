package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.DashboardSummaryDto;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DashboardService(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public DashboardSummaryDto getDashboardSummary() {
        BigDecimal totalRevenue = orderRepository.calculateTotalRevenue();
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();
        long totalCustomers = userRepository.countByRole(Role.ROLE_CUSTOMER);
        long lowStockCount = productRepository.countByStockQuantityLessThanEqual(5);

        return new DashboardSummaryDto(
                totalRevenue != null ? totalRevenue : BigDecimal.ZERO,
                totalOrders,
                totalProducts,
                totalCustomers,
                lowStockCount,
                orderRepository.findTop5ByOrderByCreatedAtDesc(),
                productRepository.findTop5ByOrderByStockQuantityAsc()
        );
    }
}
