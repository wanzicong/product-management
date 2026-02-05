# Docker 部署指南

本文档介绍如何使用 Docker 部署商品管理中心。

## 目录结构

```
product-management/
├── Dockerfile                 # 应用镜像构建文件
├── docker-compose.yml         # 完整环境配置
├── docker-compose.dev.yml     # 开发环境配置（仅MySQL）
├── docker-compose.prod.yml    # 生产环境配置
├── docker-start.bat           # Windows 启动脚本
├── docker-start.sh            # Linux 启动脚本
├── .env.example               # 环境变量模板
├── .dockerignore              # Docker 忽略文件
├── docker/
│   └── mysql/
│       └── my.cnf             # MySQL 配置
└── .github/
    └── workflows/
        └── ci-cd.yml          # GitHub Actions CI/CD
```

## 快速开始

### 方式一：使用启动脚本（推荐）

**Windows:**
```cmd
# 启动开发环境（仅 MySQL）
docker-start.bat dev

# 启动完整环境
docker-start.bat up

# 停止服务
docker-start.bat down

# 查看状态
docker-start.bat status
```

**Linux/Mac:**
```bash
chmod +x docker-start.sh

# 启动开发环境
./docker-start.sh dev

# 启动完整环境
./docker-start.sh up

# 停止服务
./docker-start.sh down
```

### 方式二：使用 docker-compose 命令

```bash
# 启动完整环境
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

## 环境说明

### 1. 开发环境 (docker-compose.dev.yml)

仅启动 MySQL 数据库，应用在本地 IDE 中运行。

```bash
docker-compose -f docker-compose.dev.yml up -d
```

- MySQL 端口: 3306
- 用户名: root
- 密码: root

### 2. 完整环境 (docker-compose.yml)

启动 MySQL + 应用服务。

```bash
docker-compose up -d --build
```

- 应用地址: http://localhost:8080
- MySQL 端口: 3307（避免与本地冲突）
- 账号: admin / admin123

### 3. 生产环境 (docker-compose.prod.yml)

用于服务器部署，包含更严格的安全配置。

```bash
# 复制环境变量文件
cp .env.example .env

# 修改 .env 中的密码等配置

# 启动服务
docker-compose -f docker-compose.prod.yml up -d
```

## CI/CD 流程

项目使用 GitHub Actions 实现自动化 CI/CD：

```
代码推送 → 构建测试 → 构建镜像 → 推送到 Registry → 部署到服务器
```

### 配置 GitHub Secrets

在 GitHub 仓库设置中添加以下 Secrets：

| Secret 名称 | 说明 |
|------------|------|
| SERVER_HOST | 服务器 IP 地址 |
| SERVER_USER | SSH 用户名 |
| SERVER_SSH_KEY | SSH 私钥 |

### 触发条件

- 推送到 `main`/`master`/`develop` 分支
- 创建 `v*` 标签
- Pull Request 到主分支

## 常用命令

```bash
# 查看容器状态
docker-compose ps

# 查看日志
docker-compose logs -f app
docker-compose logs -f mysql

# 进入容器
docker exec -it product-app sh
docker exec -it product-mysql mysql -uroot -p

# 重启服务
docker-compose restart app

# 重新构建
docker-compose up -d --build

# 清理
docker-compose down -v --rmi all
docker system prune -f
```

## 数据持久化

数据存储在 Docker 卷中：

| 卷名称 | 说明 |
|--------|------|
| product-mysql-data | MySQL 数据 |
| product-app-upload | 上传文件 |
| product-app-logs | 应用日志 |

备份数据：
```bash
# 备份 MySQL
docker exec product-mysql mysqldump -uroot -p product_management > backup.sql

# 备份上传文件
docker cp product-app:/app/upload ./backup-upload
```

## 故障排查

### 1. 应用无法连接数据库

```bash
# 检查 MySQL 是否启动
docker-compose ps mysql

# 查看 MySQL 日志
docker-compose logs mysql

# 检查网络
docker network ls
docker network inspect product-network
```

### 2. 端口被占用

```bash
# 查看端口占用
netstat -ano | findstr :8080
netstat -ano | findstr :3306

# 修改 docker-compose.yml 中的端口映射
```

### 3. 内存不足

```bash
# 查看容器资源使用
docker stats

# 调整 JVM 参数
# 修改 docker-compose.yml 中的 JAVA_OPTS
```

## 性能优化

1. **JVM 调优**: 根据服务器内存调整 `-Xms` 和 `-Xmx`
2. **MySQL 调优**: 修改 `docker/mysql/my.cnf`
3. **使用 SSD**: 将 Docker 数据目录放在 SSD 上
4. **启用 BuildKit**: `DOCKER_BUILDKIT=1 docker-compose build`
