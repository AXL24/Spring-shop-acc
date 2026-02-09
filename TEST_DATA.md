# Test Data Documentation

## Overview

This document describes the test data available in the database for API testing.

## Test Users

| ID | Username | Email | Password | Role | Active |
|----|----------|-------|----------|------|--------|
| 1 | admin | admin@example.com | Password123 | ADMIN | ✅ |
| 2 | johndoe | john@example.com | Password123 | CUSTOMER | ✅ |
| 3 | janedoe | jane@example.com | Password123 | CUSTOMER | ✅ |
| 4 | bob_seller | bob@example.com | Password123 | CUSTOMER | ✅ |
| 5 | alice_shop | alice@example.com | Password123 | CUSTOMER | ✅ |
| 6 | testuser | test@example.com | Test1234 | CUSTOMER | ❌ |

## Categories (6 total)

1. **Gaming Accounts** - Premium gaming platform accounts
2. **Streaming Services** - Entertainment streaming subscriptions
3. **Music Streaming** - Music platform premium accounts
4. **Cloud Storage** - Cloud storage services
5. **VPN Services** - VPN premium subscriptions
6. **Educational Platforms** - Online learning platforms

## Products (18 total)

### Gaming Accounts
- Minecraft Premium Account ($29.99, stock: 50)
- Steam Wallet Code $50 ($50.00, stock: 100)
- Valorant Account Level 30+ ($45.00, stock: 20)
- Epic Games Account ($15.00, stock: 30)

### Streaming Services
- Netflix Premium 1 Month ($15.99, stock: 80)
- Disney+ Annual Subscription ($79.99, stock: 40)
- HBO Max Monthly ($14.99, stock: 60)
- Amazon Prime Video 6 Months ($59.99, stock: 25)

### Music Streaming
- Spotify Premium 1 Year ($99.99, stock: 150)
- Apple Music 3 Months ($29.99, stock: 70)
- YouTube Music Premium ($9.99, stock: 90)

### Cloud Storage
- Google Drive 100GB Annual ($19.99, stock: 200)
- Dropbox Plus 1 Year ($119.99, stock: 50)
- OneDrive 1TB Lifetime ($149.99, stock: 15)

### VPN Services
- NordVPN 2 Years ($89.99, stock: 100)
- ExpressVPN 1 Year ($99.99, stock: 75)

### Educational
- Udemy Course Bundle ($59.99, stock: 40)
- Coursera Plus Annual ($399.00, stock: 20)

## Virtual Goods Accounts (23 total)

- **18 AVAILABLE** - Ready for delivery
- **3 SOLD** - Already delivered
- **2 CONTACT** - Need manual handling

## Sample Orders (6 total)

### Order 1 (COMPLETED)
- **Code**: ORD-20260205-001
- **User**: johndoe
- **Total**: $75.98
- **Items**: 2x Minecraft + 1x Valorant
- **Status**: Delivered ✅

### Order 2 (COMPLETED)
- **Code**: ORD-20260205-002
- **User**: janedoe
- **Total**: $119.99
- **Items**: 1x Dropbox Plus
- **Status**: Delivered ✅

### Order 3 (COMPLETED)
- **Code**: ORD-20260205-003
- **User**: bob_seller
- **Total**: $29.99
- **Items**: 1x Spotify Premium
- **Status**: Delivered ✅

### Order 4 (PENDING)
- **Code**: ORD-20260206-001
- **User**: johndoe
- **Total**: $131.97
- **Items**: 2x Netflix + 1x Spotify Annual
- **Status**: Awaiting delivery 🕐

### Order 5 (PENDING)
- **Code**: ORD-20260206-002
- **User**: alice_shop
- **Total**: $89.99
- **Items**: 1x NordVPN
- **Status**: Awaiting delivery 🕐

### Order 6 (CANCELLED)
- **Code**: ORD-20260206-003
- **User**: janedoe
- **Total**: $45.00
- **Items**: 1x Valorant
- **Status**: Cancelled ❌

## Quick Test Scenarios

### 1. Test User Login
```json
POST /api/v1/user/add
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "Password123"
}
```

### 2. Browse Gaming Products
```
GET /api/v1/product/getAll?page=0&size=10
```

### 3. Get User's Orders
```
GET /api/v1/order/user/2
```
(Should return orders for johndoe)

### 4. Check Available Accounts for Minecraft (Product ID: 1)
```
GET /api/v1/account/product/1/available
```
(Should return 4 available accounts)

### 5. Create New Order
```json
POST /api/v1/order
{
  "userId": 2,
  "orderItems": [
    {"productId": 1, "quantity": 1}
  ],
  "customerNote": "Test order"
}
```

## Loading Test Data

The test data is automatically loaded when you start Docker:

```bash
# Stop and remove existing containers
docker-compose down -v

# Start fresh with test data
docker-compose up -d

# Wait for MySQL to initialize (~30 seconds)
# Check logs
docker-compose logs mysql
```

The files in `/db` folder are executed in alphabetical order:
1. `database.sql` - Creates schema
2. `test_data.sql` - Inserts test data

## Notes

⚠️ **Password Security**: Test data uses plain text passwords. In production, implement BCrypt hashing.

⚠️ **Data Reset**: Running `docker-compose down -v` will delete all data and reload test data on next startup.

✅ **Ready to Test**: All endpoints can be tested immediately with this data!
