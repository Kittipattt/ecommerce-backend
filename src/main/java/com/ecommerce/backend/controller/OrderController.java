package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiResponse;
import com.ecommerce.backend.dto.OrderCreateRequest;
import com.ecommerce.backend.dto.OrderStatusUpdateRequest;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Order placed successfully", order));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders() {
        List<Order> orders = orderService.getCurrentUserOrders();
        return ResponseEntity.ok(ApiResponse.ok("User orders", orders));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<Order>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Order> orders = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(ApiResponse.ok("All orders", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.ok(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest request) {
        Order updated = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.ok("Order status updated successfully", updated));
    }
}
