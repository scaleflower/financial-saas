#!/bin/bash
BASE_DIR="/opt/financial-saas"

case "${1:-status}" in
    status)
        echo "=== 服务状态 ==="
        ss -tlnp 2>/dev/null | grep -E "8080|876[0-9]|8770|9200" | while read line; do
            port=$(echo "$line" | awk '{print $4}' | cut -d: -f2)
            case $port in
                8080) echo "tenant-service: $port - ONLINE" ;;
                8762) echo "user-service: $port - ONLINE" ;;
                8763) echo "org-service: $port - ONLINE" ;;
                8764) echo "trans-service: $port - ONLINE" ;;
                8765) echo "loan-service: $port - ONLINE" ;;
                8766) echo "repayment-service: $port - ONLINE" ;;
                8767) echo "approval-service: $port - ONLINE" ;;
                8768) echo "notification-service: $port - ONLINE" ;;
                8769) echo "report-service: $port - ONLINE" ;;
                8770) echo "file-service: $port - ONLINE" ;;
                9200) echo "gateway-service: $port - ONLINE" ;;
            esac
        done
        ;;
    pull)
        echo "=== 拉取最新代码 ==="
        cd $BASE_DIR
        git fetch origin
        git reset --hard origin/main
        git pull origin main
        ;;
    build)
        echo "=== Maven 构建 ==="
        cd $BASE_DIR
        mvn clean package -DskipTests
        ;;
    *)
        echo "CI/CD 脚本"
        echo "用法: $0 {status|pull|build}"
        ;;
esac
