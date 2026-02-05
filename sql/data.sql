-- 初始化数据
-- 设置字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

USE product_management;

-- 初始化管理员用户 (密码: admin123, MD5: 0192023a7bbd73250516f069df18b500)
INSERT INTO t_user (username, password, real_name, phone, status) VALUES
('admin', '0192023a7bbd73250516f069df18b500', '系统管理员', '13800138000', 1);

-- 初始化分类数据
INSERT INTO t_category (category_name, parent_id, level, sort_order, status) VALUES
('电子产品', 0, 1, 1, 1),
('服装鞋帽', 0, 1, 2, 1),
('食品饮料', 0, 1, 3, 1),
('家居用品', 0, 1, 4, 1);

INSERT INTO t_category (category_name, parent_id, level, sort_order, status) VALUES
('手机', 1, 2, 1, 1),
('电脑', 1, 2, 2, 1),
('平板', 1, 2, 3, 1),
('男装', 2, 2, 1, 1),
('女装', 2, 2, 2, 1),
('零食', 3, 2, 1, 1),
('饮料', 3, 2, 2, 1);

-- 初始化品牌数据
INSERT INTO t_brand (brand_name, description, sort_order, status) VALUES
('苹果', 'Apple Inc. 美国科技公司', 1, 1),
('华为', '华为技术有限公司', 2, 1),
('小米', '小米科技有限责任公司', 3, 1),
('三星', 'Samsung 韩国电子公司', 4, 1),
('耐克', 'Nike 运动品牌', 5, 1),
('阿迪达斯', 'Adidas 运动品牌', 6, 1);

-- 初始化商品数据
INSERT INTO t_product (product_code, product_name, category_id, brand_id, price, cost_price, stock, stock_warning, unit, status) VALUES
('P001', 'iPhone 15 Pro Max 256GB', 5, 1, 9999.00, 8500.00, 100, 10, '台', 1),
('P002', 'iPhone 15 128GB', 5, 1, 5999.00, 5000.00, 150, 10, '台', 1),
('P003', 'Huawei Mate 60 Pro', 5, 2, 6999.00, 5800.00, 80, 10, '台', 1),
('P004', '小米14 Ultra', 5, 3, 5999.00, 4800.00, 120, 10, '台', 1),
('P005', 'MacBook Pro 14寸 M3', 6, 1, 14999.00, 12000.00, 50, 5, '台', 1),
('P006', 'iPad Pro 12.9寸', 7, 1, 8999.00, 7500.00, 60, 5, '台', 1),
('P007', '华为MatePad Pro', 7, 2, 4999.00, 4000.00, 70, 5, '台', 1),
('P008', '耐克Air Max运动鞋', 8, 5, 899.00, 500.00, 200, 20, '双', 1),
('P009', '阿迪达斯运动T恤', 8, 6, 299.00, 150.00, 300, 30, '件', 1),
('P010', '库存预警测试商品', 5, 3, 1999.00, 1500.00, 5, 10, '台', 1);
