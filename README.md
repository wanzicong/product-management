# 商品管理中心

基于 Spring Boot 3.2 + Thymeleaf + MyBatis-Plus 的前后端不分离商品管理系统。

## 技术栈

- **后端框架**: Spring Boot 3.2.2
- **模板引擎**: Thymeleaf
- **ORM框架**: MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0
- **前端框架**: Bootstrap 5.3
- **构建工具**: Maven

## 功能模块

- ✅ 用户登录/登出
- ✅ 首页仪表盘（数据统计）
- ✅ 商品管理（CRUD、上下架、批量操作）
- ✅ 分类管理（树形结构）
- ✅ 品牌管理
- ✅ 库存管理（入库/出库、库存预警）
- ✅ 操作日志

## 快速开始

### 1. 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库初始化

```sql
-- 执行 sql/schema.sql 创建数据库和表
-- 执行 sql/data.sql 初始化测试数据
```

### 3. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/product_management
    username: root
    password: your_password
```

### 4. 启动项目

```bash
cd product-management
mvn spring-boot:run
```

### 5. 访问系统

- 地址: http://localhost:8080
- 账号: admin
- 密码: admin123

## 项目结构

```
product-management/
├── src/main/java/com/example/product/
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── service/         # 服务层
│   ├── mapper/          # 数据访问层
│   ├── entity/          # 实体类
│   ├── dto/             # 数据传输对象
│   ├── common/          # 公共类
│   ├── enums/           # 枚举类
│   ├── interceptor/     # 拦截器
│   └── util/            # 工具类
├── src/main/resources/
│   ├── mapper/          # MyBatis XML
│   ├── static/          # 静态资源
│   ├── templates/       # Thymeleaf模板
│   └── application.yml  # 配置文件
└── sql/                 # 数据库脚本
```

## 默认账号

| 用户名 | 密码 | 说明 |
|--------|------|------|
| admin | admin123 | 管理员 |

## 截图预览

- 登录页面
- 首页仪表盘
- 商品列表
- 分类管理
- 库存预警

## License

MIT
