#!/bin/bash

# 财务报销SaaS系统 - 批量启动脚本
# 使用方法: ./start-all-services.sh {start|stop|restart|status}

BASE_DIR="/opt/financial-saas"
LOG_DIR="/tmp"

# 服务列表: 名称:端口
declare -A SERVICES=(
    ["tenant-service"]="8080"
    ["user-service"]="8762"
    ["org-service"]="8763"
    ["trans-service"]="8764"
    ["loan-service"]="8765"
    ["repayment-service"]="8766"
    ["approval-service"]="8767"
    ["notification-service"]="8768"
    ["report-service"]="8769"
    ["file-service"]="8770"
    ["gateway-service"]="9200"
)

# 服务路径
declare -A SERVICE_PATHS=(
    ["tenant-service"]="$BASE_DIR/services/tenant-service"
    ["user-service"]="$BASE_DIR/services/user-service"
    ["org-service"]="$BASE_DIR/services/org-service"
    ["trans-service"]="$BASE_DIR/services/trans-service"
    ["loan-service"]="$BASE_DIR/services/loan-service"
    ["repayment-service"]="$BASE_DIR/services/repayment-service"
    ["approval-service"]="$BASE_DIR/services/approval-service"
    ["notification-service"]="$BASE_DIR/services/notification-service"
    ["report-service"]="$BASE_DIR/services/report-service"
    ["file-service"]="$BASE_DIR/services/file-service"
    ["gateway-service"]="$BASE_DIR/gateway/gateway-service"
)

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 检查服务状态
check_service() {
    local name=$1
    local port=$2
    if ss -tlnp 2>/dev/null | grep -q ":$port "; then
        echo -e "${GREEN}✓${NC} $name (port $port)"
        return 0
    else
        echo -e "${RED}✗${NC} $name (port $port)"
        return 1
    fi
}

# 启动服务
start_service() {
    local name=$1
    local path=$2
    local log_file="$LOG_DIR/${name}.log"
    
    echo -n "Starting $name... "
    
    cd "$path" || return 1
    nohup mvn spring-boot:run > "$log_file" 2>&1 &
    
    # 等待启动
    for i in {1..60}; do
        if tail -20 "$log_file" 2>/dev/null | grep -q "Started"; then
            echo -e "${GREEN}OK${NC}"
            return 0
        fi
        sleep 1
    done
    
    echo -e "${YELLOW}TIMEOUT${NC}"
    return 1
}

# 停止服务
stop_service() {
    local name=$1
    local port=$2
    
    # 查找并终止进程
    local pid=$(ss -tlnp 2>/dev/null | grep ":$port " | grep -oP 'pid=\K[0-9]+')
    if [ -n "$pid" ]; then
        kill "$pid"
        echo -e "${YELLOW}Stopped${NC} $name (pid $pid)"
    fi
}

# 显示状态
show_status() {
    echo "=== 服务状态 ==="
    for name in "${!SERVICES[@]}"; do
        check_service "$name" "${SERVICES[$name]}"
    done
}

# 启动所有服务
start_all() {
    echo "=== 启动所有服务 ==="
    
    # 按顺序启动基础设施依赖的服务
    local order=(
        "tenant-service"
        "user-service"
        "org-service"
        "trans-service"
        "loan-service"
        "repayment-service"
        "approval-service"
        "notification-service"
        "report-service"
        "file-service"
        "gateway-service"
    )
    
    for name in "${order[@]}"; do
        start_service "$name" "${SERVICE_PATHS[$name]}"
        sleep 2
    done
    
    echo ""
    echo "=== 最终状态 ==="
    sleep 5
    show_status
}

# 停止所有服务
stop_all() {
    echo "=== 停止所有服务 ==="
    for name in "${!SERVICES[@]}"; do
        stop_service "$name" "${SERVICES[$name]}"
    done
}

# 重启所有服务
restart_all() {
    stop_all
    sleep 3
    start_all
}

# 主逻辑
case "${1:-start}" in
    start)
        start_all
        ;;
    stop)
        stop_all
        ;;
    restart)
        restart_all
        ;;
    status)
        show_status
        ;;
    *)
        echo "使用方法: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac
