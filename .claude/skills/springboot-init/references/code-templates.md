# 代码模板参考

## 1. Entity 实体类模板

```java
package com.example.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_{模块名}")
public class {实体名} implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字段说明
     */
    private String fieldName;

    /**
     * 状态: 0禁用 1启用
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 逻辑删除: 0否 1是
     */
    @TableLogic
    private Integer deleted;

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

    // ========== 非数据库字段 ==========

    /**
     * 关联字段
     */
    @TableField(exist = false)
    private String relatedName;
}
```

## 2. Mapper 接口模板

```java
package com.example.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.product.entity.{实体名};
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface {实体名}Mapper extends BaseMapper<{实体名}> {
}
```

## 3. Service 接口模板

```java
package com.example.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.{实体名};

public interface {实体名}Service extends IService<{实体名}> {

    /**
     * 分页查询
     */
    IPage<{实体名}> page(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 保存实体
     */
    void save{实体名}({实体名} entity);

    /**
     * 更新实体
     */
    void update{实体名}({实体名} entity);

    /**
     * 删除实体
     */
    void delete{实体名}(Long id);

    /**
     * 批量删除
     */
    void batchDelete(java.util.List<Long> ids);

    /**
     * 根据ID获取详情
     */
    {实体名} getDetail(Long id);
}
```

## 4. Service 实现类模板

```java
package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.entity.{实体名};
import com.example.product.mapper.{实体名}Mapper;
import com.example.product.service.{实体名}Service;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class {实体名}ServiceImpl extends ServiceImpl<{实体名}Mapper, {实体名}> implements {实体名}Service {

    @Override
    public IPage<{实体名}> page(Integer pageNum, Integer pageSize, String keyword) {
        Page<{实体名}> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<{实体名}> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like({实体名}::getFieldName, keyword);
        }

        wrapper.orderByDesc({实体名}::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void save{实体名}({实体名} entity) {
        save(entity);
    }

    @Override
    public void update{实体名}({实体名} entity) {
        updateById(entity);
    }

    @Override
    public void delete{实体名}(Long id) {
        removeById(id);
    }

    @Override
    public void batchDelete(java.util.List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public {实体名} getDetail(Long id) {
        return getById(id);
    }
}
```

## 5. Controller 模板

```java
package com.example.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.common.Result;
import com.example.product.entity.{实体名};
import com.example.product.service.{实体名}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{模块名}")
public class {实体名}Controller {

    @Autowired
    private {实体名}Service {模块名}Service;

    /**
     * 列表页面
     */
    @GetMapping("/list")
    public String list() {
        return "{模块名}/list";
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @ResponseBody
    public Result<IPage<{实体名}>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        IPage<{实体名}> page = {模块名}Service.page(pageNum, pageSize, keyword);
        return Result.success(page);
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String add() {
        return "{模块名}/add";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit/{{id}}")
    public String edit(@PathVariable Long id, Model model) {
        {实体名} entity = {模块名}Service.getDetail(id);
        model.addAttribute("entity", entity);
        return "{模块名}/edit";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Void> save(@RequestBody {实体名} entity) {
        {模块名}Service.save{实体名}(entity);
        return Result.success("保存成功", null);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody {实体名} entity) {
        {模块名}Service.update{实体名}(entity);
        return Result.success("更新成功", null);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{{id}}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        {模块名}Service.delete{实体名}(id);
        return Result.success("删除成功", null);
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody java.util.List<Long> ids) {
        {模块名}Service.batchDelete(ids);
        return Result.success("批量删除成功", null);
    }
}
```

## 6. DTO 模板

### QueryDTO - 查询条件
```java
package com.example.product.dto;

import lombok.Data;

@Data
public class {实体名}QueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String keyword;
    private Integer status;
}
```

### AddDTO - 新增请求
```java
package com.example.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class {实体名}AddDTO {
    @NotBlank(message = "字段不能为空")
    private String fieldName;
}
```

## 7. 配置类模板

### MyMetaObjectHandler - 自动填充
```java
package com.example.product.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
```

### GlobalExceptionHandler - 全局异常
```java
package com.example.product.config;

import com.example.product.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(e.getMessage());
    }
}
```
