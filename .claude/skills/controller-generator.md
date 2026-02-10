---
name: controller-generator
description: 为Spring Boot项目生成Controller层代码。支持生成完整的CRUD操作、分页查询、条件搜索等标准接口。使用此技能时，需要提供实体名称（如Product、Category等）和需要的功能列表。
---

基于当前项目的代码风格和架构规范，生成符合项目标准的Controller层代码。

## 技术栈
- Spring Boot 3.2.2
- Java 17
- MyBatis-Plus 3.5.5

## 生成规则
1. **Controller位置**: `src/main/java/com/example/product/controller/`
2. **命名规范**: `{实体名}Controller.java`
3. **注解使用**:
   - `@RestController` - RESTful控制器
   - `@RequestMapping("/api/{实体名小写}")` - 基础路径
   - `@RequiredArgsConstructor` - 构造器注入Service

## 标准CRUD接口模板

```java
package com.example.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.product.common.Result;
import com.example.product.dto.{实体名}QueryDTO;
import com.example.product.entity.{实体名};
import com.example.product.service.{实体名}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{实体名小写}")
@RequiredArgsConstructor
public class {实体名}Controller {

    private final {实体名}Service {实体名小写}Service;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<{实体名}>> page({实体名}QueryDTO dto) {
        return Result.success({实体名小写}Service.page(dto));
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result<{实体名}> getById(@PathVariable Long id) {
        return Result.success({实体名小写}Service.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public Result<Void> add(@RequestBody {实体名} {实体名小写}) {
        {实体名小写}Service.save({实体名小写});
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping
    public Result<Void> update(@RequestBody {实体名} {实体名小写}) {
        {实体名小写}Service.updateById({实体名小写});
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        {实体名小写}Service.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        {实体名小写}Service.removeByIds(ids);
        return Result.success();
    }

    /**
     * 获取全部列表
     */
    @GetMapping("/list")
    public Result<List<{实体名}>> list() {
        return Result.success({实体名小写}Service.list());
    }
}
```

## 使用说明
调用此技能时，请提供以下信息：
1. **实体名称**: 如 Product、Category、Brand 等
2. **需要的功能**: 从标准CRUD接口中选择需要的接口
3. **特殊需求**: 如需要额外的自定义接口，请说明

## 注意事项
- 生成前会先检查是否已存在同名Controller
- 遵循项目的统一返回格式 `Result<T>`
- 分页查询使用 `Page<T>` 和 `QueryDTO` 模式
- 所有接口使用RESTful风格
