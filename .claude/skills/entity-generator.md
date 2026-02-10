---
name: entity-generator
description: 为Spring Boot项目生成JPA实体类。支持MyBatis-Plus注解、逻辑删除、自动填充等功能。使用此技能时，需要提供表名、字段信息和表名前缀（如t_）。
---

基于当前项目的代码风格和架构规范，生成符合项目标准的实体类。

## 技术栈
- Spring Boot 3.2.2
- Java 17
- MyBatis-Plus 3.5.5
- MySQL 8.0+

## 生成规则
1. **Entity位置**: `src/main/java/com/example/product/entity/`
2. **命名规范**: `{表名去掉前缀后首字母大写}.java` (如 t_product → Product)
3. **包结构**: entity, enums（枚举类型放这里）

## 实体类模板

```java
package com.example.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("{表名}")
public class {实体名} {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字段说明
     */
    @TableField("{字段名}")
    private String {驼峰字段名};

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 0-未删除 1-已删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
```

## 常用注解说明

| 注解 | 说明 | 使用场景 |
|------|------|----------|
| `@TableName` | 指定表名 | 类名与表名不一致时使用 |
| `@TableId` | 主键标识 | 标记主键字段 |
| `@TableField` | 字段映射 | 字段名与属性名不一致时使用 |
| `@TableLogic` | 逻辑删除 | 标记逻辑删除字段 |
| `@TableField(fill=...)` | 自动填充 | 标记需要自动填充的字段 |

## 字段类型映射

| MySQL类型 | Java类型 | 示例 |
|-----------|----------|------|
| VARCHAR | String | private String name; |
| INT | Integer | private Integer age; |
| BIGINT | Long | private Long id; |
| DECIMAL | java.math.BigDecimal | private BigDecimal price; |
| DATETIME/TIMESTAMP | java.time.LocalDateTime | private LocalDateTime createTime; |
| DATE | java.time.LocalDate | private LocalDate birthDate; |
| TINYINT | Integer | private Integer status; |

## 使用说明
调用此技能时，请提供以下信息：
1. **表名**: 如 t_product、t_category
2. **表名前缀**: 如 t_ (用于生成类名时去除)
3. **字段信息**: 字段名、类型、注释
4. **特殊配置**:
   - 是否需要逻辑删除
   - 是否需要自动填充（创建时间、更新时间）

## 注意事项
- 所有实体类使用 `@Data` 注解 (Lombok)
- 主键使用 `IdType.AUTO` 自增策略
- 逻辑删除字段固定为 `is_deleted` (0-未删除, 1-已删除)
- 时间字段统一使用 `LocalDateTime` 类型
- 枚举类型字段单独创建在 `enums` 包下
