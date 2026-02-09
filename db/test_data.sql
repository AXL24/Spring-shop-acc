-- ================================================
-- TEST DATA FOR E-COMMERCE VIRTUAL GOODS PLATFORM
-- ================================================
-- This file contains sample data for testing the API
-- It will be automatically loaded when Docker container starts

USE mydatabase;

-- ================================================
-- 1. INSERT USERS
-- ================================================
-- Password: "Password123" (in production, this should be hashed)
INSERT INTO users (username, email, password_hash, phone_number, role, active) VALUES
('admin', 'admin@example.com', 'Password123', '+1234567890', 'ADMIN', TRUE),
('johndoe', 'john@example.com', 'Password123', '+1234567891', 'CUSTOMER', TRUE),
('janedoe', 'jane@example.com', 'Password123', '+1234567892', 'CUSTOMER', TRUE),
('bob_seller', 'bob@example.com', 'Password123', '+1234567893', 'CUSTOMER', TRUE),
('alice_shop', 'alice@example.com', 'Password123', '+1234567894', 'CUSTOMER', TRUE),
('testuser', 'test@example.com', 'Test1234', '+1234567895', 'CUSTOMER', FALSE); -- Inactive user

-- ================================================
-- 2. INSERT CATEGORIES
-- ================================================
INSERT INTO categories (name, description) VALUES
('Gaming Accounts', 'Premium gaming platform accounts and credentials'),
('Streaming Services', 'Entertainment streaming platform subscriptions'),
('Music Streaming', 'Music streaming platform premium accounts'),
('Cloud Storage', 'Cloud storage and file sharing services'),
('VPN Services', 'Virtual Private Network premium subscriptions'),
('Educational Platforms', 'Online learning and course platform access');

-- ================================================
-- 3. INSERT PRODUCTS
-- ================================================
INSERT INTO products (category_id, name, description, platform, price, stock, active) VALUES
-- Gaming Accounts
(1, 'Minecraft Premium Account', 'Full access Minecraft Java Edition account with multiplayer support', 'PC', 29.99, 50, TRUE),
(1, 'Steam Wallet Code $50', 'Steam digital gift card for game purchases', 'Steam', 50.00, 100, TRUE),
(1, 'Valorant Account - Level 30+', 'Pre-leveled Valorant account ready for ranked play', 'PC', 45.00, 20, TRUE),
(1, 'Epic Games Account', 'Epic Games account with verified email', 'PC', 15.00, 30, TRUE),

-- Streaming Services
(2, 'Netflix Premium 1 Month', 'Netflix Premium subscription with 4K streaming', 'Netflix', 15.99, 80, TRUE),
(2, 'Disney+ Annual Subscription', 'One year access to Disney+ streaming platform', 'Disney+', 79.99, 40, TRUE),
(2, 'HBO Max Monthly', 'HBO Max premium streaming with latest shows', 'HBO Max', 14.99, 60, TRUE),
(2, 'Amazon Prime Video 6 Months', 'Half-year Amazon Prime Video access', 'Amazon', 59.99, 25, TRUE),

-- Music Streaming
(3, 'Spotify Premium 1 Year', 'Ad-free Spotify with offline downloads', 'Spotify', 99.99, 150, TRUE),
(3, 'Apple Music 3 Months', 'Apple Music premium access for 3 months', 'Apple Music', 29.99, 70, TRUE),
(3, 'YouTube Music Premium', 'YouTube Music with background play', 'YouTube Music', 9.99, 90, TRUE),

-- Cloud Storage
(4, 'Google Drive 100GB Annual', 'Google Drive cloud storage expansion', 'Google Drive', 19.99, 200, TRUE),
(4, 'Dropbox Plus 1 Year', 'Dropbox Plus with 2TB storage', 'Dropbox', 119.99, 50, TRUE),
(4, 'OneDrive 1TB Lifetime', 'Microsoft OneDrive storage for life', 'OneDrive', 149.99, 15, TRUE),

-- VPN Services
(5, 'NordVPN 2 Years', 'NordVPN premium subscription for 2 years', 'NordVPN', 89.99, 100, TRUE),
(5, 'ExpressVPN 1 Year', 'ExpressVPN annual subscription', 'ExpressVPN', 99.99, 75, TRUE),

-- Educational Platforms
(6, 'Udemy Course Bundle', 'Access to 5 premium Udemy courses', 'Udemy', 59.99, 40, TRUE),
(6, 'Coursera Plus Annual', 'One year unlimited access to Coursera courses', 'Coursera', 399.00, 20, TRUE),

-- Inactive product for testing
(1, 'Discontinued Game Account', 'No longer available', 'PC', 9.99, 0, FALSE);

-- ================================================
-- 4. INSERT VIRTUAL GOODS ACCOUNTS (Inventory)
-- ================================================
-- Gaming Accounts - Minecraft
INSERT INTO accounts (product_id, username, password, status) VALUES
(1, 'minecraft_player_001', 'MinecraftPass123!', 'AVAILABLE'),
(1, 'minecraft_player_002', 'MinecraftPass456!', 'AVAILABLE'),
(1, 'minecraft_player_003', 'MinecraftPass789!', 'AVAILABLE'),
(1, 'minecraft_player_004', 'MinecraftPass321!', 'SOLD'),
(1, 'minecraft_player_005', 'MinecraftPass654!', 'AVAILABLE'),

-- Valorant Accounts
(3, 'valorant_bronze_001', 'ValorantBr0nze!', 'AVAILABLE'),
(3, 'valorant_bronze_002', 'ValorantBr0nze2!', 'AVAILABLE'),
(3, 'valorant_silver_001', 'ValorantS1lver!', 'SOLD'),

-- Epic Games Accounts
(4, 'epic_verified_001', 'EpicGames2024!', 'AVAILABLE'),
(4, 'epic_verified_002', 'EpicGames2024@', 'AVAILABLE'),
(4, 'epic_verified_003', 'EpicGames2024#', 'CONTACT'),

-- Streaming - Netflix
(5, 'netflix_prem_001@temp.com', 'NetflixPrem123!', 'AVAILABLE'),
(5, 'netflix_prem_002@temp.com', 'NetflixPrem456!', 'AVAILABLE'),
(5, 'netflix_prem_003@temp.com', 'NetflixPrem789!', 'AVAILABLE'),

-- Spotify
(9, 'spotify_001@music.com', 'SpotifyPrem123!', 'AVAILABLE'),
(9, 'spotify_002@music.com', 'SpotifyPrem456!', 'AVAILABLE'),
(9, 'spotify_003@music.com', 'SpotifyPrem789!', 'SOLD'),
(9, 'spotify_004@music.com', 'SpotifyPrem321!', 'AVAILABLE'),

-- VPN Services
(15, 'nordvpn_user_001', 'NordVPN2024Secure!', 'AVAILABLE'),
(15, 'nordvpn_user_002', 'NordVPN2024Secure@', 'AVAILABLE'),
(16, 'expressvpn_001', 'ExpressVPN2024!', 'AVAILABLE');

-- ================================================
-- 5. INSERT ORDERS
-- ================================================
INSERT INTO orders (order_code, user_id, total_amount, status, customer_note) VALUES
-- Completed orders
('ORD-20260205-001', 2, 75.98, 'COMPLETED', 'Please deliver to email quickly'),
('ORD-20260205-002', 3, 119.99, 'COMPLETED', 'Rush delivery needed'),
('ORD-20260205-003', 4, 29.99, 'COMPLETED', NULL),

-- Pending orders
('ORD-20260206-001', 2, 199.98, 'PENDING', 'Multiple items order'),
('ORD-20260206-002', 5, 89.99, 'PENDING', 'First time buyer'),

-- Cancelled order
('ORD-20260206-003', 3, 45.00, 'CANCELLED', 'Customer requested cancellation');

-- ================================================
-- 6. INSERT ORDER ITEMS
-- ================================================
-- Order 1: User 2 bought Minecraft + Valorant (COMPLETED)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price, delivered) VALUES
(1, 1, 2, 29.99, 59.98, '2026-02-05 10:30:00'),  -- 2x Minecraft
(1, 3, 1, 45.00, 45.00, '2026-02-05 10:30:00');  -- 1x Valorant (marked as delivered)

-- Order 2: User 3 bought Dropbox (COMPLETED)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price, delivered) VALUES
(2, 13, 1, 119.99, 119.99, '2026-02-05 11:00:00');

-- Order 3: User 4 bought Spotify (COMPLETED)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price, delivered) VALUES
(3, 9, 1, 29.99, 29.99, '2026-02-05 12:00:00');

-- Order 4: User 2 bought Netflix + Spotify (PENDING)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price, delivered) VALUES
(4, 5, 2, 15.99, 31.98, NULL),   -- 2x Netflix (not delivered yet)
(4, 9, 1, 99.99, 99.99, NULL);   -- 1x Spotify Annual (not delivered yet)

-- Order 5: User 5 bought NordVPN (PENDING)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price, delivered) VALUES
(5, 15, 1, 89.99, 89.99, NULL);

-- Order 6: User 3's cancelled order (Valorant)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price, delivered) VALUES
(6, 3, 1, 45.00, 45.00, NULL);

-- ================================================
-- 7. INSERT TOKENS (for future authentication)
-- ================================================
INSERT INTO tokens (user_id, token, expire, revoked) VALUES
(1, 'admin_token_12345_valid_until_march', '2026-03-01 00:00:00', FALSE),
(2, 'johndoe_token_67890_valid_until_march', '2026-03-01 00:00:00', FALSE),
(3, 'janedoe_token_abcde_valid_until_march', '2026-03-01 00:00:00', FALSE),
(2, 'johndoe_old_token_revoked', '2026-02-01 00:00:00', TRUE);

-- ================================================
-- TEST DATA SUMMARY
-- ================================================
-- Users: 6 (5 active, 1 inactive)
-- Categories: 6
-- Products: 18 (17 active, 1 inactive)
-- Accounts: 23 (18 available, 3 sold, 2 contact)
-- Orders: 6 (3 completed, 2 pending, 1 cancelled)
-- Order Items: 9
-- Tokens: 4 (3 active, 1 revoked)
-- ================================================

-- Verify data insertion
SELECT 'Test data inserted successfully!' as Status;
SELECT 
    (SELECT COUNT(*) FROM users) as total_users,
    (SELECT COUNT(*) FROM categories) as total_categories,
    (SELECT COUNT(*) FROM products) as total_products,
    (SELECT COUNT(*) FROM accounts) as total_accounts,
    (SELECT COUNT(*) FROM orders) as total_orders,
    (SELECT COUNT(*) FROM order_items) as total_order_items,
    (SELECT COUNT(*) FROM tokens) as total_tokens;
