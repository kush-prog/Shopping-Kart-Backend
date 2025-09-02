🛒 Shopping Kart Backend

A backend application for managing a shopping cart system, built with Spring Boot and Java. It provides secure and scalable REST APIs for handling users, products, authentication, and cart operations.

🚀 Features

User authentication with JWT and Spring Security

Manage products, users, and cart items

Secure API endpoints with role-based access

Database integration for persistence

Follows clean, modular architecture

🛠️ Tech Stack

Java 17+

Spring Boot 3.x

Spring Security + JWT

Spring Data JPA

PostgreSQL/MySQL (configurable)

Maven

📌 API Endpoints
🔐 Authentication
Method	Endpoint	Description
POST	/api/auth/register	Register a new user
POST	/api/auth/login	Authenticate & get JWT
👤 User Management
Method	Endpoint	Description
GET	/api/users	Get all users
GET	/api/users/{id}	Get user by ID
PUT	/api/users/{id}	Update user
DELETE	/api/users/{id}	Delete user
📦 Product Management
Method	Endpoint	Description
GET	/api/products	Get all products
GET	/api/products/{id}	Get product by ID
POST	/api/products	Add new product
PUT	/api/products/{id}	Update product
DELETE	/api/products/{id}	Delete product
🛒 Cart Management
Method	Endpoint	Description
GET	/api/cart/{userId}	Get cart for a user
POST	/api/cart/{userId}/add	Add product to cart
PUT	/api/cart/{userId}/update	Update product quantity
DELETE	/api/cart/{userId}/remove/{productId}	Remove product from cart
⚙️ Setup Instructions

Clone the repository

git clone https://github.com/kush-prog/Shopping-Kart-Backend.git
cd Shopping-Kart-Backend


Configure Database
Update application.properties with your database credentials. Example for PostgreSQL:

spring.datasource.url=jdbc:postgresql://localhost:5432/shoppingkart
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update


Run the application

mvn spring-boot:run


Access API at:

http://localhost:8080/api

📂 Project Structure
src/main/java/com/shoppingkart
├── controller   # REST controllers
├── service      # Business logic
├── repository   # Data access layer
├── model        # Entities
└── security     # JWT + Spring Security


# Shopping Kart API End points

## Cart Endpoints
```
GET /carts/{cartId}/my-cart
DELETE /carts/{cartId}/clear
GET /carts/{cartId}/cart/total-price
```

## Cart Item Endpoints
```
POST /cartItems/item/add
DELETE /cartItems/cart/{cartId}/item/{itemId}/remove
PUT /cartItems/cart/{cartId}/item/{itemId}/update
```

## Category Endpoints
```
GET /categories/all
POST /categories/add
GET /categories/category/{id}/category
GET /categories/category/{name}/category
DELETE /categories/category/{id}/delete
PUT /categories/category/{id}/update
```

## Image Endpoints
```
POST /images/upload
GET /images/{imageId}
PUT /images/image/{imageId}/update
DELETE /images/image/{imageId}/delete
```

## Order Endpoints
```
POST /orders/order
GET /orders/order/{orderId}
GET /orders/order/{userId}
```

## Product Endpoints
```
GET /products/all
GET /products/product/{productId}/product
POST /products/add
PUT /products/product/{productId}/update
DELETE /products/product/{productId}/delete
GET /products/product/by/brand-and-name
GET /products/product/by/category-and-brand
GET /products/product/{name}/products
GET /products/product/by-brand
GET /products/product/{category}/all/products
GET /products/product/count/by-brand/and-name
```

## User Endpoints
```
GET /users/user/{userId}
POST /users/create
PUT /users/update/{userId}
DELETE /users/delete/{userId}
```
