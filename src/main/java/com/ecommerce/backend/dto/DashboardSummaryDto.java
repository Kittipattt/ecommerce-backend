package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import java.math.BigDecimal;
import java.util.List;

public class DashboardSummaryDto {
    private BigDecimal totalRevenue;
    private long totalOrders;
    private long totalProducts;
    private long totalCustomers;
    private long lowStockCount;
    private List<Order> recentOrders;
    private List<Product> lowStockProducts;

    public DashboardSummaryDto() {}

    public DashboardSummaryDto(BigDecimal totalRevenue, long totalOrders, long totalProducts, long totalCustomers, long lowStockCount, List<Order> recentOrders, List<Product> lowStockProducts) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalProducts = totalProducts;
        this.totalCustomers = totalCustomers;
        this.lowStockCount = lowStockCount;
        this.recentOrders = recentOrders;
        this.lowStockProducts = lowStockProducts;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(long lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

    public List<Order> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<Order> recentOrders) {
        this.recentOrders = recentOrders;
    }

    public List<Product> getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(List<Product> lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }
}
