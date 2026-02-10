# SQL 模板参考

## 表结构设计规范

### 命名规范
- 表名: `t_{模块名}` (小写，下划线分隔)
- 字段名: 小写，下划线分隔 (如 `create_time`)
- 主键: `id BIGINT AUTO_INCREMENT`
- 逻辑删除: `is_deleted TINYINT DEFAULT 0`
- 创建时间: `create_time DATETIME`
- 更新时间: `update_time DATETIME`

## 通用表模板

```sql
-- {表名中文说明}
DROP TABLE IF EXISTS `t_{模块名}`;
CREATE TABLE `t_{模块名}` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `field_name` VARCHAR(255) NOT NULL COMMENT '字段说明',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序值',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0否 1是',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='{表名中文说明}';
```

## 常用字段类型

| MySQL类型 | Java类型 | 说明 |
|-----------|----------|------|
| `BIGINT` | `Long` | 主键、外键 |
| `VARCHAR(255)` | `String` | 短文本 |
| `TEXT` | `String` | 长文本 |
| `TINYINT` | `Integer` | 状态、枚举 |
| `INT` | `Integer` | 数量、排序 |
| `DECIMAL(10,2)` | `BigDecimal` | 金额 |
| `DATETIME` | `LocalDateTime` | 时间 |
| `DATE` | `LocalDate` | 日期 |

## 示例：用户表

```sql
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `real_name` VARCHAR(50) COMMENT '真实姓名',
  `phone` VARCHAR(20) COMMENT '手机号',
  `email` VARCHAR(100) COMMENT '邮箱',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
```

## 示例：商品表

```sql
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_code` VARCHAR(50) NOT NULL COMMENT '商品编码',
  `product_name` VARCHAR(200) NOT NULL COMMENT '商品名称',
  `category_id` BIGINT COMMENT '分类ID',
  `brand_id` BIGINT COMMENT '品牌ID',
  `price` DECIMAL(10,2) NOT NULL COMMENT '销售价格',
  `cost_price` DECIMAL(10,2) COMMENT '成本价格',
  `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  `stock_warning` INT NOT NULL DEFAULT 10 COMMENT '库存预警值',
  `unit` VARCHAR(20) COMMENT '计量单位',
  `main_image` VARCHAR(500) COMMENT '主图URL',
  `images` TEXT COMMENT '详情图JSON数组',
  `description` TEXT COMMENT '商品描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0下架 1上架',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序值',
  `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_code` (`product_code`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_brand_id` (`brand_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';
```

## 初始数据模板

```sql
-- {表名中文说明} 初始数据
INSERT INTO `t_{模块名}` (`id`, `field_name`, `status`, `sort_order`, `create_time`, `update_time`) VALUES
(1, '数据1', 1, 1, NOW(), NOW()),
(2, '数据2', 1, 2, NOW(), NOW());
```

## 索引设计建议

| 索引类型 | 使用场景 |
|---------|----------|
| `PRIMARY KEY` | 主键 |
| `UNIQUE KEY` | 唯一约束 (如用户名、编码) |
| `KEY` | 普通索引 (外键、状态、时间) |
| `INDEX` | 组合索引 (多字段查询) |

### 组合索引规则
- 最左前缀原则
- 区分度高的字段放前面
- 避免冗余索引
