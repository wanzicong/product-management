#!/bin/bash
# 商品管理中心 Docker 启动脚本

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 打印带颜色的消息
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 显示帮助
show_help() {
    echo "商品管理中心 Docker 管理脚本"
    echo ""
    echo "用法: ./docker-start.sh [命令]"
    echo ""
    echo "命令:"
    echo "  dev         启动开发环境（仅 MySQL）"
    echo "  up          启动完整环境（MySQL + 应用）"
    echo "  down        停止所有服务"
    echo "  restart     重启所有服务"
    echo "  logs        查看日志"
    echo "  build       重新构建镜像"
    echo "  clean       清理容器和镜像"
    echo "  status      查看服务状态"
    echo "  mysql       进入 MySQL 命令行"
    echo "  help        显示帮助"
    echo ""
}

# 启动开发环境
start_dev() {
    print_info "启动开发环境（仅 MySQL）..."
    docker-compose -f docker-compose.dev.yml up -d
    print_info "MySQL 启动完成！"
    print_info "连接信息: localhost:3306, 用户: root, 密码: root"
}

# 启动完整环境
start_up() {
    print_info "启动完整环境..."
    docker-compose up -d --build
    print_info "等待服务启动..."
    sleep 10
    print_info "服务启动完成！"
    print_info "访问地址: http://localhost:8080"
    print_info "账号: admin, 密码: admin123"
}

# 停止服务
stop_down() {
    print_info "停止所有服务..."
    docker-compose down
    docker-compose -f docker-compose.dev.yml down 2>/dev/null || true
    print_info "服务已停止"
}

# 重启服务
restart_services() {
    print_info "重启服务..."
    docker-compose restart
    print_info "服务已重启"
}

# 查看日志
show_logs() {
    docker-compose logs -f --tail=100
}

# 重新构建
rebuild() {
    print_info "重新构建镜像..."
    docker-compose build --no-cache
    print_info "构建完成"
}

# 清理
clean_all() {
    print_warn "即将清理所有容器和镜像..."
    read -p "确认继续? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        docker-compose down -v --rmi all
        docker-compose -f docker-compose.dev.yml down -v 2>/dev/null || true
        docker system prune -f
        print_info "清理完成"
    fi
}

# 查看状态
show_status() {
    print_info "服务状态:"
    docker-compose ps
    echo ""
    print_info "容器资源使用:"
    docker stats --no-stream --format "table {{.Name}}\t{{.CPUPerc}}\t{{.MemUsage}}" 2>/dev/null || true
}

# 进入 MySQL
enter_mysql() {
    print_info "进入 MySQL 命令行..."
    docker exec -it product-mysql mysql -uroot -proot123456 product_management 2>/dev/null || \
    docker exec -it product-mysql-dev mysql -uroot -proot product_management
}

# 主逻辑
case "${1:-help}" in
    dev)
        start_dev
        ;;
    up)
        start_up
        ;;
    down)
        stop_down
        ;;
    restart)
        restart_services
        ;;
    logs)
        show_logs
        ;;
    build)
        rebuild
        ;;
    clean)
        clean_all
        ;;
    status)
        show_status
        ;;
    mysql)
        enter_mysql
        ;;
    help|*)
        show_help
        ;;
esac
