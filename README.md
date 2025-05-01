# Shopping Kart API End points

Base URL: `https://shopping-kart-backend-production.up.railway.app/kush/shopping-kart`

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
