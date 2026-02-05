@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

:: 商品管理中心 Docker 启动脚本 (Windows)

echo ========================================
echo   商品管理中心 Docker 管理脚本
echo ========================================
echo.

if "%1"=="" goto help
if "%1"=="dev" goto dev
if "%1"=="up" goto up
if "%1"=="down" goto down
if "%1"=="restart" goto restart
if "%1"=="logs" goto logs
if "%1"=="build" goto build
if "%1"=="status" goto status
if "%1"=="mysql" goto mysql
if "%1"=="clean" goto clean
if "%1"=="help" goto help
goto help

:dev
echo [INFO] 启动开发环境（仅 MySQL）...
docker-compose -f docker-compose.dev.yml up -d
echo.
echo [INFO] MySQL 启动完成！
echo [INFO] 连接信息: localhost:3306
echo [INFO] 用户: root, 密码: root
goto end

:up
echo [INFO] 启动完整环境...
docker-compose up -d --build
echo.
echo [INFO] 等待服务启动...
timeout /t 15 /nobreak >nul
echo.
echo ========================================
echo   服务启动完成！
echo   访问地址: http://localhost:8080
echo   账号: admin
echo   密码: admin123
echo ========================================
goto end

:down
echo [INFO] 停止所有服务...
docker-compose down
docker-compose -f docker-compose.dev.yml down 2>nul
echo [INFO] 服务已停止
goto end

:restart
echo [INFO] 重启服务...
docker-compose restart
echo [INFO] 服务已重启
goto end

:logs
docker-compose logs -f --tail=100
goto end

:build
echo [INFO] 重新构建镜像...
docker-compose build --no-cache
echo [INFO] 构建完成
goto end

:status
echo [INFO] 服务状态:
docker-compose ps
echo.
echo [INFO] 容器资源使用:
docker stats --no-stream --format "table {{.Name}}\t{{.CPUPerc}}\t{{.MemUsage}}"
goto end

:mysql
echo [INFO] 进入 MySQL 命令行...
docker exec -it product-mysql mysql -uroot -proot123456 product_management 2>nul || docker exec -it product-mysql-dev mysql -uroot -proot product_management
goto end

:clean
echo [WARN] 即将清理所有容器和镜像...
set /p confirm="确认继续? (y/n): "
if /i "%confirm%"=="y" (
    docker-compose down -v --rmi all
    docker-compose -f docker-compose.dev.yml down -v 2>nul
    docker system prune -f
    echo [INFO] 清理完成
)
goto end

:help
echo 用法: docker-start.bat [命令]
echo.
echo 命令:
echo   dev         启动开发环境（仅 MySQL）
echo   up          启动完整环境（MySQL + 应用）
echo   down        停止所有服务
echo   restart     重启所有服务
echo   logs        查看日志
echo   build       重新构建镜像
echo   status      查看服务状态
echo   mysql       进入 MySQL 命令行
echo   clean       清理容器和镜像
echo   help        显示帮助
echo.
echo 示例:
echo   docker-start.bat dev    # 启动开发数据库
echo   docker-start.bat up     # 启动完整项目
echo   docker-start.bat down   # 停止项目
goto end

:end
endlocal
