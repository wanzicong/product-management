---
name: service-generator
description: 为Spring Boot项目生成Service层代码（接口+实现类）。基于MyBatis-Plus封装标准的CRUD操作。使用此技能时，需要提供实体名称和自定义业务方法。
---

基于当前项目的代码风格和架构规范，生成符合项目标准的Service层代码。

## 技术栈
- Spring Boot 3.2.2
- Java 17
- MyBatis-Plus 3.5.5

## 生成规则
1. **Service接口位置**: `src/main/java/com/example/product/service/`
2. **Service实现位置**: `src/main/java/com/example/product/service/impl/`
3. **命名规范**:
   - 接口: `{实体名}Service.java`
   - 实现: `{实体名}ServiceImpl.java`

## Service接口模板

```java
package com.example.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.dto.{实体名}QueryDTO;
import com.example.product.entity.{实体名};

public interface {实体名}Service extends IService<{实体名}> {

    /**
     * 分页查询
     */
    Page<{实体名}> page({实体名}QueryDTO dto);
}
```

## Service实现类模板

```java
package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.dto.{实体名}QueryDTO;
import com.example.product.entity.{实体名};
import com.example.product.mapper.{实体名}Mapper;
import com.example.product.service.{实体名}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class {实体名}ServiceImpl extends ServiceImpl<{实体名}Mapper, {实体名}> implements {实体名}Service {

    @Override
    public Page<{实体名}> page({实体名}QueryDTO dto) {
        Page<{实体名}> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<{实体名}> wrapper = new LambdaQueryWrapper<>();

        // TODO: 根据DTO字段添加查询条件
        // wrapper.like(StringUtils.hasText(dto.getKeyword()), {实体名}::getName, dto.getKeyword());

        wrapper.orderByDesc({实体名}::getCreateTime);
        return page(page, wrapper);
    }
}
```

## 使用说明
调用此技能时，请提供以下信息：
1. **实体名称**: 如 Product、Category、Brand 等
2. **查询条件**: 需要在分页查询中支持哪些搜索条件
3. **自定义方法**: 是否需要额外的业务方法

## 注意事项
- Service接口继承 `IService<T>` (MyBatis-Plus接口)
- ServiceImpl继承 `ServiceImpl<Mapper, Entity>` 并实现Service接口
- 使用 `@Service` 注解标记实现类
- 使用 `LambdaQueryWrapper` 构建类型安全的查询条件
- 分页查询返回 `Page<T>` 对象
