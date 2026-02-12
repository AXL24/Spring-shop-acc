# E-commerce Backend API Documentation

## Overview

TODO: add account, purchase logic , update statuses...


This is a RESTful API for an e-commerce platform specializing in **virtual goods delivery**. The system manages products, categories, users, orders, and virtual goods (accounts/credentials).

**Base URL**: `http://localhost:8080`

**Content-Type**: `application/json`

---

## Authentication

> **Note**: Authentication endpoints are not yet implemented. All endpoints are currently public.

---

## Error Handling

All errors follow a consistent format:

```json
{
  "status": 404,
  "message": "User not found with id: 123",
  "timestamp": "2026-02-06T14:30:00Z",
  "path": "/api/v1/user/123"
}
```

### HTTP Status Codes

- `200 OK` - Successful GET, PUT, PATCH requests
- `201 Created` - Successful POST requests
- `204 No Content` - Successful DELETE requests
- `400 Bad Request` - Validation errors or invalid input
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

---

## User Endpoints

### Create User

**POST** `/api/v1/user/add`

Create a new user account.

**Request Body**:
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "Password123",
  "phoneNumber": "+1234567890",
  "role": "CUSTOMER"
}
```

**Validation Rules**:
- `username`: Required, 3-50 characters
- `email`: Required, valid email format, max 100 characters
- `password`: Required, min 8 characters, must contain at least 1 uppercase letter
- `phoneNumber`: Optional, max 15 characters, digits and +-()\s only
- `role`: Optional (default: CUSTOMER), values: CUSTOMER, ADMIN, SELLER

**Response** (201 Created):
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "phoneNumber": "+1234567890",
  "role": "CUSTOMER",
  "active": true,
  "created": "2026-02-06T14:30:00Z",
  "updated": "2026-02-06T14:30:00Z"
}
```

### Get User by ID

**GET** `/api/v1/user/{id}`

**Response** (200 OK):
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "phoneNumber": "+1234567890",
  "role": "CUSTOMER",
  "active": true,
  "created": "2026-02-06T14:30:00Z",
  "updated": "2026-02-06T14:30:00Z"
}
```

### Get All Users

**GET** `/api/v1/user/getAll?page=0&size=10`

**Query Parameters**:
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)

**Response** (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "username": "johndoe",
      "email": "john@example.com",
      "phoneNumber": "+1234567890",
      "role": "CUSTOMER",
      "active": true,
      "created": "2026-02-06T14:30:00Z",
      "updated": "2026-02-06T14:30:00Z"
    }
  ],
  "totalElements": 50,
  "totalPages": 5,
  "size": 10,
  "number": 0
}
```

### Update User

**PUT** `/api/v1/user/{id}`

**Request Body**: Same as Create User

**Response** (200 OK): Updated user object

### Deactivate User (Soft Delete)

**PATCH** `/api/v1/user/{id}/deactivate`

Sets `active=false` without removing from database.

**Response** (204 No Content)

### Delete User (Hard Delete)

**DELETE** `/api/v1/user/{id}`

Permanently removes user from database.

**Response** (204 No Content)

---

## Category Endpoints

### Create Category

**POST** `/api/v1/category/add`

**Content-Type**: `multipart/form-data`

Create a new category with optional image upload.

**Request Parameters**:
- `category` (required, JSON part): Category data
- `image` (optional, file part): Category image file

**Category JSON Structure**:
```json
{
  "name": "Gaming Accounts",
  "description": "Virtual gaming accounts and credentials"
}
```

**Validation Rules**:
- `name`: Required, 2-100 characters
- `description`: Optional, max 500 characters
- `image`: Optional, supported image formats (JPEG, PNG, etc.)

**Example using cURL**:
```bash
curl -X POST http://localhost:8080/api/v1/category/add \
  -F 'category={"name":"Gaming Accounts","description":"Virtual gaming accounts and credentials"};type=application/json' \
  -F 'image=@/path/to/image.jpg'
```

**Response** (201 Created):
```json
{
  "id": 1,
  "name": "Gaming Accounts",
  "description": "Virtual gaming accounts and credentials",
  "image_url": "http://localhost:8080/uploads/categories/uuid-image.jpg",
  "created": "2026-02-06T14:30:00Z"
}
```

### Get Category by ID

**GET** `/api/v1/category/{id}`

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Gaming Accounts",
  "description": "Virtual gaming accounts and credentials",
  "image_url": "http://localhost:8080/uploads/categories/uuid-image.jpg",
  "created": "2026-02-06T14:30:00Z"
}
```

### Get All Categories

**GET** `/api/v1/category/getAll?page=0&size=10`

**Query Parameters**:
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)

**Response** (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "name": "Gaming Accounts",
      "description": "Virtual gaming accounts and credentials",
      "image_url": "http://localhost:8080/uploads/categories/uuid-image.jpg",
      "created": "2026-02-06T14:30:00Z"
    }
  ],
  "totalElements": 10,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

### Update Category

**PUT** `/api/v1/category/{id}`

**Content-Type**: `multipart/form-data`

Update a category with optional image replacement. If a new image is provided, the old image will be automatically deleted.

**Request Parameters**:
- `category` (required, JSON part): Updated category data
- `image` (optional, file part): New category image file

**Example using cURL**:
```bash
curl -X PUT http://localhost:8080/api/v1/category/1 \
  -F 'category={"name":"Updated Gaming Accounts","description":"Updated description"};type=application/json' \
  -F 'image=@/path/to/new-image.jpg'
```

**Response** (200 OK): Updated category object

### Delete Category

**DELETE** `/api/v1/category/{id}`

Deletes the category and its associated image file (if exists).

**Response** (204 No Content)

---

## Product Endpoints

### Create Product

**POST** `/api/v1/product/add`

**Request Body**:
```json
{
  "name": "Minecraft Premium Account",
  "price": 29.99,
  "category_id": 1,
  "stock": 50,
  "platform": "PC",
  "description": "Full access Minecraft Java Edition account"
}
```

**Validation Rules**:
- `name`: Required, 3-100 characters
- `price`: Required, 0-1,000,000
- `category_id`: Required, must exist
- `stock`: Optional (default: 0)
- `platform`: Optional, max 50 characters
- `description`: Optional

**Response** (201 Created):
```json
{
  "id": 1,
  "name": "Minecraft Premium Account",
  "price": 29.99,
  "category_id": 1,
  "category_name": "Gaming Accounts",
  "stock": 50,
  "platform": "PC",
  "description": "Full access Minecraft Java Edition account",
  "active": true,
  "created": "2026-02-06T14:30:00Z",
  "updated": "2026-02-06T14:30:00Z"
}
```

### Get Product by ID

**GET** `/api/v1/product/findById/{id}`

### Get All Products

**GET** `/api/v1/product/getAll?page=0&size=10`

### Update Product

**PUT** `/api/v1/product/{id}`

### Deactivate Product (Soft Delete)

**PATCH** `/api/v1/product/{id}/deactivate`

### Delete Product (Hard Delete)

**DELETE** `/api/v1/product/{id}`

---

## Product Image Endpoints

### Get Product Images

**GET** `/api/v1/product_img/{product_id}`

Returns all images associated with a specific product.

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "image_url": "http://localhost:8080/uploads/products/1739370000_abcd.jpg"
  }
]
```

### Upload Product Images (Batch)

**POST** `/api/v1/product_img/{product_id}/uploads`

**Content-Type**: `multipart/form-data`

Upload multiple images for a product at once.

**Request Parameters**:
- `files` (required, file part): One or more image files.

**Validation Rules**:
- `product_id`: Must exist.
- `files`: At least one file required.
- Total images per product cannot exceed 5.

**Example using cURL**:
```bash
curl -X POST http://localhost:8080/api/v1/product_img/1/uploads \
  -F "files=@/path/to/image1.jpg" \
  -F "files=@/path/to/image2.jpg"
```

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "image_url": "http://localhost:8080/uploads/products/1739370000_abcd.jpg"
  },
  {
    "id": 2,
    "image_url": "http://localhost:8080/uploads/products/1739370001_efgh.jpg"
  }
]
```



---

## Order Endpoints

### Create Order

**POST** `/api/v1/order`

Creates a new order with automatic total calculation and stock management.

**Request Body**:
```json
{
  "userId": 1,
  "customerNote": "Please deliver quickly",
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**Validation Rules**:
- `userId`: Required, must exist
- `orderItems`: Required, at least 1 item
- `orderItems[].productId`: Required, must exist
- `orderItems[].quantity`: Required, min 1
- `customerNote`: Optional, max 1000 characters

**Response** (201 Created):
```json
{
  "id": 1,
  "orderCode": "ORD-1738838400000-A1B2C3D4",
  "userId": 1,
  "username": "johndoe",
  "totalAmount": 89.97,
  "status": "PENDING",
  "customerNote": "Please deliver quickly",
  "orderItems": [
    {
      "id": 1,
      "productId": 1,
      "productName": "Minecraft Premium Account",
      "quantity": 2,
      "unitPrice": 29.99,
      "totalPrice": 59.98,
      "delivered": null
    },
    {
      "id": 2,
      "productId": 2,
      "productName": "Spotify Premium 1 Year",
      "quantity": 1,
      "unitPrice": 29.99,
      "totalPrice": 29.99,
      "delivered": null
    }
  ],
  "created": "2026-02-06T14:30:00Z",
  "updated": "2026-02-06T14:30:00Z"
}
```

**Business Logic**:
- Automatically generates unique order code
- Calculates total amount from order items
- Validates product stock availability
- Decrements product stock for each item
- Sets initial status to "PENDING"

### Get Order by ID

**GET** `/api/v1/order/{id}`

### Get All Orders

**GET** `/api/v1/order/getAll?page=0&size=10`

### Get User's Orders

**GET** `/api/v1/order/user/{userId}`

Returns all orders for a specific user.

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "orderCode": "ORD-1738838400000-A1B2C3D4",
    "userId": 1,
    "username": "johndoe",
    "totalAmount": 89.97,
    "status": "DELIVERED",
    "customerNote": "Please deliver quickly",
    "orderItems": [...],
    "created": "2026-02-06T14:30:00Z",
    "updated": "2026-02-06T14:31:00Z"
  }
]
```

### Update Order Status

**PATCH** `/api/v1/order/{id}/status`

**Request Body**:
```json
{
  "status": "PROCESSING"
}
```

**Valid Status Values**: PENDING, PROCESSING, DELIVERED, CANCELLED

**Response** (200 OK): Updated order object

### Delete Order

**DELETE** `/api/v1/order/{id}`

---

## Order Item Endpoints

### Create Order Item

**POST** `/api/v1/order-item`

**Request Body**:
```json
{
  "orderId": 1,
  "productId": 1,
  "quantity": 2
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "orderId": 1,
  "productId": 1,
  "productName": "Minecraft Premium Account",
  "quantity": 2,
  "unitPrice": 29.99,
  "totalPrice": 59.98
}
```

### Get Order Item by ID

**GET** `/api/v1/order-item/{id}`

### Get Items by Order ID

**GET** `/api/v1/order-item/order/{orderId}`

---

## Account Endpoints (Virtual Goods)

Accounts represent virtual goods to be delivered to customers.

### Create Account

**POST** `/api/v1/account`

**Request Body**:
```json
{
  "productId": 1,
  "username": "player123",
  "password": "SecurePass456",
  "status": "AVAILABLE"
}
```

**Validation Rules**:
- `productId`: Required, must exist
- `username`: Required, max 500 characters
- `password`: Required, max 500 characters
- `status`: Optional (default: AVAILABLE), values: AVAILABLE, SOLD, CONTACT

**Response** (201 Created):
```json
{
  "id": 1,
  "productId": 1,
  "productName": "Minecraft Premium Account",
  "username": "player123",
  "password": "SecurePass456",
  "status": "AVAILABLE",
  "sold": null,
  "created": "2026-02-06T14:30:00Z"
}
```

### Get Account by ID

**GET** `/api/v1/account/{id}`

### Get All Accounts

**GET** `/api/v1/account/getAll?page=0&size=10`

### Get Accounts by Product

**GET** `/api/v1/account/product/{productId}`

Returns all accounts for a specific product.

### Get Available Accounts by Product

**GET** `/api/v1/account/product/{productId}/available`

Returns only accounts with status="AVAILABLE" for a specific product.

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "productId": 1,
    "productName": "Minecraft Premium Account",
    "username": "player123",
    "password": "SecurePass456",
    "status": "AVAILABLE",
    "sold": null,
    "created": "2026-02-06T14:30:00Z"
  }
]
```

### Update Account

**PUT** `/api/v1/account/{id}`

**Request Body**: Same as Create Account

When status is changed to "SOLD", the `sold` timestamp is automatically set.

**Response** (200 OK): Updated account object

### Delete Account

**DELETE** `/api/v1/account/{id}`

---

## Validation Error Example

**Request** (Invalid data):
```json
{
  "username": "ab",
  "email": "invalid-email",
  "password": "short"
}
```

**Response** (400 Bad Request):
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "username": "Username must be between 3 and 50 characters",
    "email": "Email should be valid",
    "password": "Password must be at least 8 characters long"
  },
  "timestamp": "2026-02-06T14:30:00Z",
  "path": "/api/v1/user/add"
}
```

---

## Common Workflows

### 1. Create a New Product

1. Create a category: `POST /api/v1/category/add` (with optional image upload)
2. Create a product with category ID: `POST /api/v1/product/add`
3. Upload product images: `POST /api/v1/product_img/{product_id}/uploads`
4. Add virtual goods (accounts): `POST /api/v1/account` (multiple times)

### 2. Place an Order

1. Browse products: `GET /api/v1/product/getAll`
2. Create order with items: `POST /api/v1/order`
3. System automatically:
   - Generates order code
   - Calculates total
   - Validates and decrements stock

### 3. Process Order (Virtual Goods Delivery)

1. Get order details: `GET /api/v1/order/{id}`
2. For each order item, get available accounts: `GET /api/v1/account/product/{productId}/available`
3. Mark accounts as sold: `PUT /api/v1/account/{id}` with `status: "SOLD"`
4. Update order status: `PATCH /api/v1/order/{id}/status` with `status: "DELIVERED"`

---

## Database Schema Notes

- **Soft Delete**: Users and Products support soft delete (setting `active=false`)
- **Hard Delete**: All entities support hard delete (permanent removal)
- **Timestamps**: All entities have `created` timestamp, most have `updated` timestamp
- **Cascading**: OrderItems are cascade-deleted when Order is deleted
- **Stock Management**: Product stock is automatically decremented when orders are created

---

## Future Enhancements

- JWT authentication and authorization
- Order item delivery tracking (mark individual items as delivered)
- QR code generation for virtual goods
- MongoDB integration for product reviews
- Redis caching for frequently accessed products
- Payment integration
