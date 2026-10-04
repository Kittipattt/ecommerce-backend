package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderCreateRequest;
import com.ecommerce.backend.dto.OrderItemRequest;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        AuthService authService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.authService = authService;
    }

    @Transactional
    public Order createOrder(OrderCreateRequest request) {
        User currentUser = authService.getCurrentUser();

        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUser(currentUser);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingName(request.getShippingName());
        order.setShippingAddress(request.getShippingAddress());
        order.setPhone(request.getPhone());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemReq.getProductId()));

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() + " (Available: " + product.getStockQuantity() + ")");
            }

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    product.getName(),
                    product.getPrice(),
                    itemReq.getQuantity()
            );

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(lineTotal);
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getCurrentUserOrders() {
        User currentUser = authService.getCurrentUser();
        return orderRepository.findByUserOrderByCreatedAtDesc(currentUser);
    }

    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}
