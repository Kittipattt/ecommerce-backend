package com.ecommerce.backend.config;

import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      CategoryRepository categoryRepository,
                      ProductRepository productRepository,
                      OrderRepository orderRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        // 1. Seed Users
        User admin = new User(
                "admin@store.com",
                passwordEncoder.encode("admin123"),
                "Admin Manager",
                Role.ROLE_ADMIN
        );
        User customer = new User(
                "customer@store.com",
                passwordEncoder.encode("customer123"),
                "John Doe",
                Role.ROLE_CUSTOMER
        );
        userRepository.saveAll(Arrays.asList(admin, customer));

        // 2. Seed Categories
        Category electronics = new Category("Electronics & Displays", "electronics", "Premium monitors, displays and computing gears", "monitor");
        Category audio = new Category("Audio & Sound", "audio", "Wireless noise-cancelling headphones and speakers", "headphones");
        Category wearables = new Category("Wearables & Smart Home", "wearables", "Smart fitness watches and ambient accessories", "watch");
        Category accessories = new Category("PC & Workspace Accessories", "accessories", "Mechanical keyboards, ergonomic mice and docks", "keyboard");
        categoryRepository.saveAll(Arrays.asList(electronics, audio, wearables, accessories));

        // 3. Seed Products
        Product p1 = new Product(
                "Sony WH-1000XM5 Wireless Headphones",
                "Industry-leading noise cancellation with two processors and 8 microphones. Up to 30-hour battery life with quick charging.",
                new BigDecimal("349.99"),
                24,
                audio,
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800&auto=format&fit=crop&q=80",
                true,
                4.9,
                142
        );

        Product p2 = new Product(
                "Keychron Q1 Pro Wireless Custom Mechanical Keyboard",
                "Full aluminum 75% layout QMK/VIA wireless mechanical keyboard with double-gasket design and hot-swappable switches.",
                new BigDecimal("199.00"),
                15,
                accessories,
                "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=800&auto=format&fit=crop&q=80",
                true,
                4.8,
                89
        );

        Product p3 = new Product(
                "Dell UltraSharp 34\" Curved USB-C Hub Monitor",
                "WQHD 3440 x 1440 curved IPS display with 90W power delivery, RJ45 ethernet port and KVM switch.",
                new BigDecimal("579.00"),
                8,
                electronics,
                "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=800&auto=format&fit=crop&q=80",
                true,
                4.7,
                64
        );

        Product p4 = new Product(
                "Apple Watch Series 9 GPS 45mm",
                "S9 SiP chip with Double Tap gesture, brighter display, and advanced health tracking sensors.",
                new BigDecimal("399.00"),
                18,
                wearables,
                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=800&auto=format&fit=crop&q=80",
                true,
                4.9,
                210
        );

        Product p5 = new Product(
                "Logitech MX Master 3S Wireless Performance Mouse",
                "Quiet clicks, 8K DPI tracking on glass, MagSpeed electromagnetic scrolling, and ergonomic thumb rest.",
                new BigDecimal("99.99"),
                32,
                accessories,
                "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=800&auto=format&fit=crop&q=80",
                false,
                4.8,
                320
        );

        Product p6 = new Product(
                "Marshall Emberton II Portable Bluetooth Speaker",
                "Rich, clear and loud 360-degree True Stereophonic sound with 30+ hours of portable playtime. IP67 dust and water-resistance.",
                new BigDecimal("169.99"),
                4, // Low stock on purpose
                audio,
                "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=800&auto=format&fit=crop&q=80",
                true,
                4.6,
                95
        );

        Product p7 = new Product(
                "Anker Prime 6-in-1 Charging Station 140W",
                "Ultra-compact GaN desktop charger with 4 USB ports and 2 AC outlets for laptop, phone, and tablet.",
                new BigDecimal("79.99"),
                3, // Low stock on purpose
                accessories,
                "https://images.unsplash.com/photo-1583863788434-e58a36330cf0?w=800&auto=format&fit=crop&q=80",
                false,
                4.7,
                51
        );

        Product p8 = new Product(
                "BenQ ScreenBar Halo LED Monitor Light",
                "Wireless controller, front and back ambient lighting, auto-dimming precision desktop eye-care lamp.",
                new BigDecimal("149.00"),
                12,
                electronics,
                "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800&auto=format&fit=crop&q=80",
                false,
                4.9,
                78
        );

        List<Product> savedProducts = productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));

        // 4. Seed Initial Sample Orders
        Order order1 = new Order();
        order1.setOrderNumber("ORD-2026-8812");
        order1.setUser(customer);
        order1.setStatus(OrderStatus.DELIVERED);
        order1.setShippingName("John Doe");
        order1.setShippingAddress("99/4 Sukhumvit Road, Khlong Toei, Bangkok 10110");
        order1.setPhone("081-234-5678");
        order1.setPaymentMethod("CREDIT_CARD");
        order1.setCreatedAt(LocalDateTime.now().minusDays(3));

        OrderItem item1 = new OrderItem(order1, p1, p1.getName(), p1.getPrice(), 1);
        OrderItem item2 = new OrderItem(order1, p5, p5.getName(), p5.getPrice(), 1);
        order1.setItems(Arrays.asList(item1, item2));
        order1.setTotalAmount(p1.getPrice().add(p5.getPrice()));

        Order order2 = new Order();
        order2.setOrderNumber("ORD-2026-9934");
        order2.setUser(customer);
        order2.setStatus(OrderStatus.PROCESSING);
        order2.setShippingName("John Doe");
        order2.setShippingAddress("99/4 Sukhumvit Road, Khlong Toei, Bangkok 10110");
        order2.setPhone("081-234-5678");
        order2.setPaymentMethod("BANK_TRANSFER");
        order2.setCreatedAt(LocalDateTime.now().minusHours(5));

        OrderItem item3 = new OrderItem(order2, p2, p2.getName(), p2.getPrice(), 1);
        order2.setItems(Arrays.asList(item3));
        order2.setTotalAmount(p2.getPrice());

        orderRepository.saveAll(Arrays.asList(order1, order2));
    }
}
