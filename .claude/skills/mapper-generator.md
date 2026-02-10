---
name: mapper-generator
description: 为Spring Boot项目生成MyBatis-Plus Mapper接口。继承BaseMapper获得标准CRUD方法。使用此技能时，需要提供实体名称。
---

基于当前项目的代码风格和架构规范，生成符合项目标准的Mapper接口。

## 技术栈
- Spring Boot 3.2.2
- Java 17
- MyBatis-Plus 3.5.5

## 生成规则
1. **Mapper位置**: `src/main/java/com/example/product/mapper/`
2. **命名规范**: `{实体名}Mapper.java`
3. **注解使用**:
   - `@Mapper` - 标记为MyBatis接口
   - 继承 `BaseMapper<T>` - 获得基础CRUD方法

## Mapper接口模板

```java
package com.example.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.product.entity.{实体名};
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface {实体名}Mapper extends BaseMapper<{实体名}> {
}
```

## BaseMapper提供的方法

继承 `BaseMapper<T>` 后，自动获得以下方法：

| 方法 | 说明 | 示例 |
|------|------|------|
| `insert(T entity)` | 插入一条记录 | mapper.insert(product); |
| `deleteById(Serializable id)` | 根据ID删除 | mapper.deleteById(1L); |
| `updateById(T entity)` | 根据ID更新 | mapper.updateById(product); |
| `selectById(Serializable id)` | 根据ID查询 | mapper.selectById(1L); |
| `selectBatchIds(Collection ids)` | 批量查询 | mapper.selectBatchIds(Arrays.asList(1,2)); |
| `selectList(Wrapper wrapper)` | 条件查询 | mapper.selectList(wrapper); |
| `selectPage(Page page, Wrapper wrapper)` | 分页查询 | mapper.selectPage(page, wrapper); |
| `selectCount(Wrapper wrapper)` | 条件计数 | mapper.selectCount(wrapper); |

## 自定义SQL方法

如需添加自定义SQL方法，在Mapper接口中声明：

```java
@Mapper
public interface {实体名}Mapper extends BaseMapper<{实体名}> {

    /**
     * 自定义查询方法
     */
    @Select("SELECT * FROM {表名} WHERE {字段} = #{value}")
    List<{实体名}> selectBy{字段}(@Param("value") String value);
}
```

## 使用说明
调用此技能时，请提供以下信息：
1. **实体名称**: 如 Product、Category、Brand 等
2. **自定义方法**: 是否需要额外的自定义查询方法

## 注意事项
- Mapper接口必须是接口 (interface)，不是类
- 必须使用 `@Mapper` 注解标记
- 继承 `BaseMapper<T>` 后无需编写XML映射文件即可使用基础CRUD
- 复杂查询可使用 `LambdaQueryWrapper` 构建条件
- 特别复杂的SQL可在 `src/main/resources/mapper/` 下创建XML文件
