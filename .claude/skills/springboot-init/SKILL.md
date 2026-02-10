---
name: springboot-init
description: 快速初始化基于 Spring Boot 3 + Thymeleaf + MyBatis-Plus 的企业级管理系统项目模板。包含完整的目录结构、配置文件、代码模板和 Docker 部署配置。当用户需要创建新的企业管理系统、CRUD 应用或内部管理平台时使用此技能。
---

## 项目模板概述

基于商品管理中心的成熟技术栈，快速搭建标准化企业管理系统。

## 技术栈

- **后端**: Spring Boot 3.2.2 + Java 17 + MyBatis-Plus 3.5.5
- **前端**: Bootstrap 5.3.2 + Thymeleaf 3.1.x
- **数据库**: MySQL 8.0+
- **构建工具**: Maven
- **容器化**: Docker + Docker Compose

## 快速开始

### 1. 创建项目骨架

```
项目根目录/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── sql/
│   ├── schema.sql
│   └── data.sql
└── src/main/
    ├── java/com/example/{项目名}/
    │   ├── common/
    │   ├── config/
    │   ├── controller/
    │   ├── dto/
    │   ├── entity/
    │   ├── enums/
    │   ├── interceptor/
    │   ├── mapper/
    │   ├── service/
    │   └── {项目名}Application.java
    └── resources/
        ├── application.yml
        ├── templates/
        │   └── layout/
        └── static/
            ├── css/
            ├── js/
            └── images/
```

### 2. 核心配置文件

使用 `assets/` 目录下的模板文件：

- `pom.xml` - Maven 依赖配置
- `application.yml` - 应用配置
- `Dockerfile` - 容器镜像构建
- `docker-compose.yml` - 服务编排

### 3. 代码模板

参考 `references/code-templates.md` 获取：
- Entity 实体类模板
- Controller 控制器模板
- Service 服务层模板
- Mapper 数据访问层模板
- DTO 数据传输对象模板
- Thymeleaf 页面模板

## 项目结构说明

### 后端包结构

| 包名 | 说明 | 内容 |
|------|------|------|
| `common` | 公共类 | Result、常量、异常 |
| `config` | 配置类 | MyBatis、Web MVC、异常处理 |
| `controller` | 控制器 | 页面路由、API 接口 |
| `dto` | 数据传输对象 | 请求/响应 DTO |
| `entity` | 实体类 | 数据库实体映射 |
| `enums` | 枚举类 | 业务枚举定义 |
| `interceptor` | 拦截器 | 登录拦截、权限控制 |
| `mapper` | 数据访问 | MyBatis-Plus Mapper |
| `service` | 服务层 | 业务逻辑 |

### 前端资源结构

```
resources/
├── templates/           # Thymeleaf 模板
│   ├── layout/         # 布局模板 (head、header、sidebar、footer)
│   └── {模块}/         # 业务页面
└── static/             # 静态资源
    ├── css/            # custom.css (自定义样式)
    ├── js/             # common.js (公共脚本)
    ├── images/         # 图片资源
    └── plugins/        # 前端插件
```

## 代码规范

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 数据库表 | `t_{模块名}` | `t_product` |
| 实体类 | 去前缀首字母大写 | `Product` |
| Mapper | `{实体}Mapper` | `ProductMapper` |
| Service接口 | `{实体}Service` | `ProductService` |
| Service实现 | `{实体}ServiceImpl` | `ProductServiceImpl` |
| Controller | `{实体}Controller` | `ProductController` |
| DTO | `{实体}QueryDTO/AddDTO/UpdateDTO` | `ProductQueryDTO` |

### 注解使用

**Entity 实体类**:
```java
@Data                           // Lombok
@TableName("t_product")          // 表名映射
public class Product {
    @TableId(type = IdType.AUTO) // 主键自增
    private Long id;

    @TableLogic                  // 逻辑删除
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT) // 创建时间自动填充
    private LocalDateTime createTime;

    @TableField(exist = false)   // 非数据库字段
    private String categoryName;
}
```

**Controller**:
```java
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String list(Model model) { return "product/list"; }

    @GetMapping("/page")
    @ResponseBody
    public Result<IPage<Product>> page(...) { ... }
}
```

## 配置要点

### MyBatis-Plus 配置
- 逻辑删除字段: `deleted` (0-未删除, 1-已删除)
- 主键策略: `AUTO` 自增
- 驼峰转换: 启用
- 自动填充: 创建时间、更新时间

### 统一返回格式
```java
Result<T> {
    code: 200,      // 状态码
    message: "操作成功",
    data: {...}     // 响应数据
}
```

### 环境变量支持
配置支持通过环境变量覆盖：
- `SPRING_DATASOURCE_URL` - 数据库连接
- `SPRING_DATASOURCE_USERNAME` - 数据库用户名
- `SPRING_DATASOURCE_PASSWORD` - 数据库密码
- `FILE_UPLOAD_PATH` - 文件上传路径

## Docker 部署

### 构建镜像
```bash
mvn clean package
docker build -t {项目名}:1.0.0 .
```

### 启动服务
```bash
docker-compose up -d
```

### 服务端口
- 应用服务: 8080
- MySQL: 3307

## 开发流程

1. **设计数据库表** → 编写 `schema.sql`
2. **生成实体类** → 使用 entity 模板
3. **创建 Mapper** → 继承 BaseMapper
4. **实现 Service** → 继承 IService
5. **编写 Controller** → 页面路由 + API 接口
6. **创建前端页面** → Thymeleaf 模板
7. **测试接口** → 使用 curl 或 Postman

## 参考文档

- **代码模板**: 见 `references/code-templates.md`
- **SQL 模板**: 见 `references/sql-templates.md`
- **前端模板**: 见 `references/frontend-templates.md`
