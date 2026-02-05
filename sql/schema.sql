-- 商品管理中心数据库脚本
-- 设置字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS product_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE product_management;

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(32) NOT NULL COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码',
    real_name VARCHAR(32) DEFAULT NULL COMMENT '真实姓名',
    phone VARCHAR(16) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
    avatar VARCHAR(256) DEFAULT NULL COMMENT '头像',
    status TINYINT DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 2. 分类表
-- ----------------------------
DROP TABLE IF EXISTS t_category;
CREATE TABLE t_category (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    category_name VARCHAR(64) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID, 0为顶级',
    level TINYINT NOT NULL DEFAULT 1 COMMENT '层级: 1/2/3',
    sort_order INT DEFAULT 0 COMMENT '排序值',
    icon VARCHAR(256) DEFAULT NULL COMMENT '分类图标',
    status TINYINT DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除: 0否 1是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- ----------------------------
-- 3. 品牌表
-- ----------------------------
DROP TABLE IF EXISTS t_brand;
CREATE TABLE t_brand (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    brand_name VARCHAR(64) NOT NULL COMMENT '品牌名称',
    logo VARCHAR(256) DEFAULT NULL COMMENT '品牌Logo',
    description VARCHAR(512) DEFAULT NULL COMMENT '品牌描述',
    sort_order INT DEFAULT 0 COMMENT '排序值',
    status TINYINT DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除: 0否 1是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品牌表';

-- ----------------------------
-- 4. 商品表
-- ----------------------------
DROP TABLE IF EXISTS t_product;
CREATE TABLE t_product (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    product_code VARCHAR(32) NOT NULL COMMENT '商品编码',
    product_name VARCHAR(128) NOT NULL COMMENT '商品名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    brand_id BIGINT DEFAULT NULL COMMENT '品牌ID',
    price DECIMAL(10,2) NOT NULL COMMENT '销售价格',
    cost_price DECIMAL(10,2) DEFAULT NULL COMMENT '成本价格',
    stock INT DEFAULT 0 COMMENT '库存数量',
    stock_warning INT DEFAULT 10 COMMENT '库存预警值',
    unit VARCHAR(16) DEFAULT NULL COMMENT '计量单位',
    weight DECIMAL(10,3) DEFAULT NULL COMMENT '重量(kg)',
    main_image VARCHAR(256) DEFAULT NULL COMMENT '主图URL',
    images TEXT DEFAULT NULL COMMENT '详情图URL(JSON数组)',
    description TEXT DEFAULT NULL COMMENT '商品描述',
    status TINYINT DEFAULT 1 COMMENT '状态: 0下架 1上架',
    sort_order INT DEFAULT 0 COMMENT '排序值',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除: 0否 1是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_product_code (product_code),
    KEY idx_category_id (category_id),
    KEY idx_brand_id (brand_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- 5. 库存记录表
-- ----------------------------
DROP TABLE IF EXISTS t_stock_record;
CREATE TABLE t_stock_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    type TINYINT NOT NULL COMMENT '类型: 1入库 2出库',
    quantity INT NOT NULL COMMENT '变动数量',
    before_stock INT NOT NULL COMMENT '变动前库存',
    after_stock INT NOT NULL COMMENT '变动后库存',
    reason VARCHAR(256) DEFAULT NULL COMMENT '变动原因',
    operator VARCHAR(64) DEFAULT NULL COMMENT '操作人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存记录表';

-- ----------------------------
-- 6. 操作日志表
-- ----------------------------
DROP TABLE IF EXISTS t_operation_log;
CREATE TABLE t_operation_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    username VARCHAR(32) DEFAULT NULL COMMENT '用户名',
    module VARCHAR(32) DEFAULT NULL COMMENT '操作模块',
    operation VARCHAR(64) DEFAULT NULL COMMENT '操作类型',
    method VARCHAR(128) DEFAULT NULL COMMENT '请求方法',
    params TEXT DEFAULT NULL COMMENT '请求参数',
    ip VARCHAR(64) DEFAULT NULL COMMENT 'IP地址',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
