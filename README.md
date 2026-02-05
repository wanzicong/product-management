# 商品管理中心

基于 Spring Boot 3.2 + Thymeleaf + MyBatis-Plus 的前后端不分离商品管理系统。

## 目录

- [技术栈](#技术栈)
- [功能模块](#功能模块)
- [项目结构](#项目结构)
- [代码规范](#代码规范)
- [开发环境搭建](#开发环境搭建)
- [运行环境部署](#运行环境部署)
- [Docker部署](#docker部署)
- [API接口说明](#api接口说明)
- [数据库设计](#数据库设计)

---

## 技术栈

| 类别 | 技术 | 版本 | 说明 |
|------|------|------|------|
| **后端框架** | Spring Boot | 3.2.2 | 核心框架 |
| **模板引擎** | Thymeleaf | 3.1.x | 服务端渲染 |
| **ORM框架** | MyBatis-Plus | 3.5.5 | 数据持久层 |
| **数据库** | MySQL | 8.0+ | 关系型数据库 |
| **连接池** | HikariCP | 5.x | 高性能连接池 |
| **前端框架** | Bootstrap | 5.3.2 | UI组件库 |
| **图标库** | Bootstrap Icons | 1.11.2 | 图标字体 |
| **构建工具** | Maven | 3.6+ | 项目构建 |
| **JDK** | Eclipse Temurin | 17+ | Java运行时 |
| **容器化** | Docker | 20.10+ | 容器部署 |

## 功能模块

- ✅ **用户认证** - 登录/登出、Session管理
- ✅ **首页仪表盘** - 数据统计、库存预警展示
- ✅ **商品管理** - CRUD、上下架、批量操作、分页搜索
- ✅ **分类管理** - 树形结构、多级分类
- ✅ **品牌管理** - 品牌CRUD、Logo上传
- ✅ **库存管理** - 入库/出库、库存预警、库存记录
- ✅ **操作日志** - 操作记录、日志查询

---

## 项目结构

```
product-management/
├── .github/
│   └── workflows/
│       └── ci-cd.yml              # GitHub Actions CI/CD配置
├── docker/
│   └── mysql/
│       └── my.cnf                 # MySQL配置文件
├── docs/
│   └── Docker部署指南.md          # Docker部署文档
├── sql/
│   ├── schema.sql                 # 数据库表结构
│   ├── data.sql                   # 初始化数据
│   └── mock_products.sql          # Mock测试数据(1000条)
├── src/
│   └── main/
│       ├── java/com/example/product/
│       │   ├── common/            # 公共类
│       │   │   ├── BusinessException.java   # 业务异常
│       │   │   ├── Constants.java           # 常量定义
│       │   │   └── Result.java              # 统一响应
│       │   ├── config/            # 配置类
│       │   │   ├── GlobalExceptionHandler.java  # 全局异常处理
│       │   │   ├── MyMetaObjectHandler.java     # MyBatis-Plus自动填充
│       │   │   ├── MybatisPlusConfig.java       # MyBatis-Plus配置
│       │   │   └── WebMvcConfig.java            # Web MVC配置
│       │   ├── controller/        # 控制器层
│       │   │   ├── BrandController.java
│       │   │   ├── CategoryController.java
│       │   │   ├── FileUploadController.java
│       │   │   ├── IndexController.java
│       │   │   ├── LogController.java
│       │   │   ├── LoginController.java
│       │   │   ├── ProductController.java
│       │   │   └── StockController.java
│       │   ├── dto/               # 数据传输对象
│       │   │   └── response/
│       │   │       └── CategoryTreeVO.java
│       │   ├── entity/            # 实体类
│       │   │   ├── Brand.java
│       │   │   ├── Category.java
│       │   │   ├── OperationLog.java
│       │   │   ├── Product.java
│       │   │   ├── StockRecord.java
│       │   │   └── User.java
│       │   ├── enums/             # 枚举类
│       │   │   ├── ProductStatusEnum.java
│       │   │   └── StockTypeEnum.java
│       │   ├── interceptor/       # 拦截器
│       │   │   └── LoginInterceptor.java
│       │   ├── mapper/            # 数据访问层
│       │   │   ├── BrandMapper.java
│       │   │   ├── CategoryMapper.java
│       │   │   ├── OperationLogMapper.java
│       │   │   ├── ProductMapper.java
│       │   │   ├── StockRecordMapper.java
│       │   │   └── UserMapper.java
│       │   ├── service/           # 服务层接口
│       │   │   ├── impl/          # 服务层实现
│       │   │   │   ├── BrandServiceImpl.java
│       │   │   │   ├── CategoryServiceImpl.java
│       │   │   │   ├── OperationLogServiceImpl.java
│       │   │   │   ├── ProductServiceImpl.java
│       │   │   │   ├── StockServiceImpl.java
│       │   │   │   └── UserServiceImpl.java
│       │   │   ├── BrandService.java
│       │   │   ├── CategoryService.java
│       │   │   ├── OperationLogService.java
│       │   │   ├── ProductService.java
│       │   │   ├── StockService.java
│       │   │   └── UserService.java
│       │   ├── util/              # 工具类
│       │   │   ├── PasswordUtil.java
│       │   │   └── SessionUtil.java
│       │   └── ProductManagementApplication.java  # 启动类
│       └── resources/
│           ├── mapper/            # MyBatis XML映射文件
│           │   ├── ProductMapper.xml
│           │   └── StockRecordMapper.xml
│           ├── static/            # 静态资源
│           │   ├── css/
│           │   │   └── custom.css
│           │   └── js/
│           │       └── common.js
│           ├── templates/         # Thymeleaf模板
│           │   ├── brand/
│           │   ├── category/
│           │   ├── error/
│           │   ├── layout/
│           │   ├── log/
│           │   ├── product/
│           │   ├── stock/
│           │   ├── index.html
│           │   └── login.html
│           └── application.yml    # 应用配置
├── .dockerignore                  # Docker忽略文件
├── .env.example                   # 环境变量示例
├── .gitignore                     # Git忽略文件
├── Dockerfile                     # Docker构建文件
├── docker-compose.yml             # Docker Compose配置
├── docker-compose.dev.yml         # 开发环境配置
├── docker-compose.prod.yml        # 生产环境配置
├── docker-start.bat               # Windows启动脚本
├── docker-start.sh                # Linux启动脚本
├── pom.xml                        # Maven配置
└── README.md                      # 项目说明
```

---

## 代码规范

### 1. 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| **包名** | 全小写，点分隔 | `com.example.product.service` |
| **类名** | 大驼峰命名 | `ProductService`, `UserController` |
| **方法名** | 小驼峰命名 | `findById()`, `saveProduct()` |
| **变量名** | 小驼峰命名 | `productName`, `categoryId` |
| **常量名** | 全大写，下划线分隔 | `MAX_PAGE_SIZE`, `LOGIN_USER` |
| **数据库表名** | 小写，下划线分隔，t_前缀 | `t_product`, `t_category` |
| **数据库字段** | 小写，下划线分隔 | `product_name`, `create_time` |

### 2. 分层架构规范

```
┌─────────────────────────────────────────────────────────┐
│                    Controller 层                         │
│  - 接收请求参数，调用Service，返回视图或JSON              │
│  - 不包含业务逻辑                                        │
├─────────────────────────────────────────────────────────┤
│                     Service 层                           │
│  - 业务逻辑处理                                          │
│  - 事务管理 (@Transactional)                            │
│  - 调用Mapper进行数据操作                                │
├─────────────────────────────────────────────────────────┤
│                     Mapper 层                            │
│  - 数据库访问                                            │
│  - 继承 BaseMapper<T> 获得CRUD能力                      │
│  - 复杂SQL使用XML映射文件                                │
├─────────────────────────────────────────────────────────┤
│                     Entity 层                            │
│  - 数据库表映射实体                                      │
│  - 使用 @TableName, @TableId, @TableField 注解          │
└─────────────────────────────────────────────────────────┘
```

### 3. 代码风格

- **缩进**: 4个空格（不使用Tab）
- **行宽**: 最大120字符
- **编码**: UTF-8
- **注释**: 类和公共方法必须有JavaDoc注释
- **日志**: 使用SLF4J + Logback

### 4. 实体类规范

```java
@Data                                    // Lombok自动生成getter/setter
@TableName("t_product")                  // 表名映射
public class Product {
    @TableId(type = IdType.AUTO)         // 主键自增
    private Long id;

    private String productName;           // 驼峰自动映射下划线

    @TableField(fill = FieldFill.INSERT) // 自动填充
    private LocalDateTime createTime;

    @TableLogic                           // 逻辑删除
    private Integer deleted;
}
```

### 5. Controller规范

```java
@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/list")                  // GET请求 - 查询
    public String list(Model model) { }

    @GetMapping("/add")                   // GET请求 - 表单页面
    public String add(Model model) { }

    @PostMapping("/save")                 // POST请求 - 保存
    public String save(Product product) { }

    @PostMapping("/delete/{id}")          // POST请求 - 删除
    @ResponseBody
    public Result delete(@PathVariable Long id) { }
}
```

---

## 开发环境搭建

### 1. 环境要求

| 软件 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | 推荐 Eclipse Temurin 17 |
| Maven | 3.6+ | 项目构建 |
| MySQL | 8.0+ | 数据库 |
| IDE | IntelliJ IDEA / VS Code | 开发工具 |
| Git | 2.x | 版本控制 |

### 2. 本地开发步骤

#### 方式一：使用本地MySQL

```bash
# 1. 克隆项目
git clone https://github.com/wanzicong/product-management.git
cd product-management

# 2. 创建数据库
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/data.sql

# 3. 修改数据库配置
# 编辑 src/main/resources/application.yml

# 4. 启动项目
mvn spring-boot:run

# 5. 访问 http://localhost:8080
```

#### 方式二：使用Docker MySQL（推荐）

```bash
# 1. 克隆项目
git clone https://github.com/wanzicong/product-management.git
cd product-management

# 2. 启动MySQL容器
docker-compose -f docker-compose.dev.yml up -d

# 3. 等待MySQL启动完成（约30秒）
docker-compose -f docker-compose.dev.yml logs -f mysql

# 4. 修改配置连接Docker MySQL
# application.yml 中修改端口为 3307

# 5. 启动项目
mvn spring-boot:run
```

### 3. IDE配置

#### IntelliJ IDEA

1. 安装插件：Lombok, MyBatisX
2. 启用注解处理：Settings → Build → Compiler → Annotation Processors → Enable
3. 设置编码：Settings → Editor → File Encodings → UTF-8

#### VS Code

1. 安装扩展：Extension Pack for Java, Spring Boot Extension Pack
2. 配置 `settings.json`:
```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.compile.nullAnalysis.mode": "automatic"
}
```

---

## 运行环境部署

### 1. 服务器要求

| 配置项 | 最低配置 | 推荐配置 |
|--------|----------|----------|
| CPU | 1核 | 2核+ |
| 内存 | 2GB | 4GB+ |
| 磁盘 | 20GB | 50GB+ |
| 操作系统 | CentOS 7+ / Ubuntu 18.04+ | Ubuntu 22.04 LTS |

### 2. 传统部署

```bash
# 1. 安装JDK 17
sudo apt update
sudo apt install openjdk-17-jdk

# 2. 安装MySQL 8.0
sudo apt install mysql-server

# 3. 初始化数据库
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/data.sql

# 4. 打包项目
mvn clean package -DskipTests

# 5. 运行
java -jar target/product-management-1.0.0.jar \
  --spring.datasource.url=jdbc:mysql://localhost:3306/product_management \
  --spring.datasource.username=root \
  --spring.datasource.password=your_password

# 6. 后台运行（使用nohup）
nohup java -jar target/product-management-1.0.0.jar > app.log 2>&1 &
```

### 3. Systemd服务配置

创建 `/etc/systemd/system/product-management.service`:

```ini
[Unit]
Description=Product Management Application
After=network.target mysql.service

[Service]
Type=simple
User=www-data
WorkingDirectory=/opt/product-management
ExecStart=/usr/bin/java -Xms256m -Xmx512m -jar product-management-1.0.0.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# 启动服务
sudo systemctl daemon-reload
sudo systemctl enable product-management
sudo systemctl start product-management
sudo systemctl status product-management
```

---

## Docker部署

### 1. 快速启动

```bash
# 一键启动（包含MySQL + 应用）
docker-compose up -d --build

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down

# 停止并删除数据
docker-compose down -v
```

### 2. 环境配置

| 文件 | 用途 |
|------|------|
| `docker-compose.yml` | 完整环境（MySQL + App） |
| `docker-compose.dev.yml` | 开发环境（仅MySQL） |
| `docker-compose.prod.yml` | 生产环境（优化配置） |

### 3. 环境变量

复制 `.env.example` 为 `.env` 并修改：

```bash
# 数据库配置
MYSQL_ROOT_PASSWORD=your_secure_password
MYSQL_DATABASE=product_management

# 应用配置
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/product_management
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_secure_password

# JVM配置
JAVA_OPTS=-Xms256m -Xmx512m
```

### 4. 生产环境部署

```bash
# 使用生产配置
docker-compose -f docker-compose.prod.yml up -d --build

# 查看运行状态
docker-compose -f docker-compose.prod.yml ps

# 查看资源使用
docker stats
```

---

## API接口说明

### 页面路由

| 路径 | 方法 | 说明 |
|------|------|------|
| `/login` | GET | 登录页面 |
| `/` | GET | 首页仪表盘 |
| `/product/list` | GET | 商品列表 |
| `/product/add` | GET | 新增商品页面 |
| `/product/edit/{id}` | GET | 编辑商品页面 |
| `/product/detail/{id}` | GET | 商品详情页面 |
| `/category/list` | GET | 分类列表 |
| `/brand/list` | GET | 品牌列表 |
| `/stock/list` | GET | 库存列表 |
| `/stock/warning` | GET | 库存预警 |
| `/stock/record` | GET | 库存记录 |
| `/log/list` | GET | 操作日志 |

### AJAX接口

| 路径 | 方法 | 说明 |
|------|------|------|
| `/product/save` | POST | 保存商品 |
| `/product/delete/{id}` | POST | 删除商品 |
| `/product/updateStatus` | POST | 更新商品状态 |
| `/product/batchDelete` | POST | 批量删除 |
| `/category/save` | POST | 保存分类 |
| `/category/delete/{id}` | POST | 删除分类 |
| `/brand/save` | POST | 保存品牌 |
| `/brand/delete/{id}` | POST | 删除品牌 |
| `/stock/adjust` | POST | 库存调整 |
| `/upload/image` | POST | 图片上传 |

---

## 数据库设计

### ER图

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   t_user    │     │ t_category  │     │   t_brand   │
├─────────────┤     ├─────────────┤     ├─────────────┤
│ id          │     │ id          │     │ id          │
│ username    │     │ category_name│    │ brand_name  │
│ password    │     │ parent_id   │     │ logo        │
│ real_name   │     │ level       │     │ description │
│ phone       │     │ sort_order  │     │ sort_order  │
│ status      │     │ status      │     │ status      │
└─────────────┘     └─────────────┘     └─────────────┘
                           │                   │
                           └─────────┬─────────┘
                                     │
                           ┌─────────▼─────────┐
                           │    t_product      │
                           ├───────────────────┤
                           │ id                │
                           │ product_code      │
                           │ product_name      │
                           │ category_id (FK)  │
                           │ brand_id (FK)     │
                           │ price             │
                           │ cost_price        │
                           │ stock             │
                           │ stock_warning     │
                           │ status            │
                           └─────────┬─────────┘
                                     │
                           ┌─────────▼─────────┐
                           │  t_stock_record   │
                           ├───────────────────┤
                           │ id                │
                           │ product_id (FK)   │
                           │ type              │
                           │ quantity          │
                           │ before_stock      │
                           │ after_stock       │
                           │ reason            │
                           │ operator          │
                           └───────────────────┘

┌───────────────────┐
│  t_operation_log  │
├───────────────────┤
│ id                │
│ user_id           │
│ username          │
│ module            │
│ operation         │
│ method            │
│ params            │
│ ip                │
└───────────────────┘
```

### 表结构说明

| 表名 | 说明 | 记录数 |
|------|------|--------|
| t_user | 用户表 | 1 |
| t_category | 分类表 | 11 |
| t_brand | 品牌表 | 6 |
| t_product | 商品表 | 1010 |
| t_stock_record | 库存记录表 | - |
| t_operation_log | 操作日志表 | - |

---

## 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |

---

## 常见问题

### 1. 中文乱码

确保数据库和连接都使用UTF-8编码：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/product_management?useUnicode=true&characterEncoding=utf8mb4
```

### 2. 时区问题

配置MySQL时区：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/product_management?serverTimezone=Asia/Shanghai
```

### 3. Docker启动失败

检查端口占用：
```bash
# 检查8080端口
netstat -tlnp | grep 8080

# 检查3306/3307端口
netstat -tlnp | grep 3306
```

---

## License

MIT License

## 贡献

欢迎提交 Issue 和 Pull Request！
