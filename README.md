# E-Commerce Backend Service (Spring Boot 3 + Java 21)

Microservice REST API for E-Commerce Storefront and Admin Management.

## Tech Stack
- **Language**: Java 21
- **Framework**: Spring Boot 3.4.3
- **Security**: Spring Security + JWT (Stateless Bearer Tokens)
- **Database**: H2 In-Memory Database (with Console enabled)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Apache Maven

---

## Getting Started

### 1. Run Backend Service
```bash
cd backend
mvn spring-boot:run
```
The server will start at: `http://localhost:8080`

### 2. Database Console (H2)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ecommercedb`
- User: `sa`
- Password: `password`

---

## Default Seed Accounts
| Role | Email | Password | Permissions |
|---|---|---|---|
| **Admin** | `admin@store.com` | `admin123` | Full Admin Dashboard, Manage Products, Update Orders |
| **Customer** | `customer@store.com` | `customer123` | Browse Store, Add to Cart, Place Orders, View My Orders |

---

## API Endpoints

### Authentication (`/api/auth`)
- `POST /api/auth/login` - Login with email & password, returns JWT token
- `POST /api/auth/register` - Register a new customer account
- `GET /api/auth/me` - Get current authenticated user profile

### Categories (`/api/categories`)
- `GET /api/categories` - List all categories
- `GET /api/categories/{id}` - Get category details

### Products (`/api/products`)
- `GET /api/products` - List products with filter (`categoryId`, `search`), pagination (`page`, `size`), sorting (`sortBy`, `sortDirection`)
- `GET /api/products/featured` - List featured products
- `GET /api/products/{id}` - Get single product details
- `POST /api/products` - Create new product *(Admin only)*
- `PUT /api/products/{id}` - Update product details *(Admin only)*
- `DELETE /api/products/{id}` - Delete product *(Admin only)*

### Orders (`/api/orders`)
- `POST /api/orders` - Place new order (Automatically verifies and deducts stock)
- `GET /api/orders/my-orders` - Get current customer's order history
- `GET /api/orders/all` - Get all orders *(Admin only)*
- `GET /api/orders/{id}` - Get single order details
- `PUT /api/orders/{id}/status` - Update order status (`PENDING`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED`) *(Admin only)*

### Admin Dashboard (`/api/admin/dashboard`)
- `GET /api/admin/dashboard` - Get KPI metrics (Total Revenue, Orders, Products, Customers, Low Stock items) *(Admin only)*
