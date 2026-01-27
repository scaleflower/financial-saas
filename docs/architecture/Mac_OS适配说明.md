# Mac OS 适配说明

## 系统信息

- **操作系统**: Mac OS
- **Shell**: zsh (默认)
- **文件系统**: APFS 或 HFS+

## 脚本适配注意事项

### 1. 路径处理

#### 绝对路径 vs 相对路径
```bash
# ❌ 避免：使用 Windows 风格路径
C:\Users\...

# ✅ 推荐：使用 Unix 风格路径
/Users/heyake/Documents/Jacky/Financial_reimbursement

# ✅ 或使用相对路径
cd /Users/heyake/Documents/Jacky/Financial_reimbursement
./scripts/migrate.sh
```

#### 环境变量
```bash
# 设置环境变量 (zsh)
export DB_HOST=localhost
export DB_PORT=3306
export DB_USER=root

# 永久设置（添加到 ~/.zshrc）
echo 'export DB_HOST=localhost' >> ~/.zshrc
source ~/.zshrc
```

### 2. 工具命令差异

#### 命令行工具
```bash
# tree 命令
# Mac OS 默认没有 tree，需要安装
brew install tree

# 或者使用 find 命令替代
find . -type d -maxdepth 3

# sed 命令
# Mac OS 的 sed 与 Linux 略有差异
# 使用 -i '' 或 -i.bak 进行备份
sed -i '' 's/old/new/g' file.txt

# grep 命令
# Mac OS 的 grep 需要加 -E 或使用 egrep
grep -E "pattern" file.txt
```

### 3. MySQL 连接

#### MySQL 命令行工具
```bash
# MySQL 在 Mac OS 上的常见安装方式
# 1. Homebrew
brew install mysql

# 2. DMG 安装包
# 3. Docker

# 连接命令
mysql -h localhost -P 3306 -u root -p'iWhale@2023' fs_enabling

# 如果端口不是 3306，使用 -P 指定
mysql -h localhost -P 3306 -u root -p'iWhale@2023' fs_enabling
```

#### MySQL Workbench
- 下载：https://dev.mysql.com/downloads/workbench/
- 连接参数：
  - Host: localhost
  - Port: 3306
  - Username: root
  - Password: iWhale@2023
  - Default Schema: fs_enabling

### 4. PostgreSQL 连接

#### PostgreSQL 命令行工具
```bash
# 连接命令
psql -h localhost -p 5432 -U heyake -d new_fs

# 如果需要输入密码
PGPASSWORD='' psql -h localhost -p 5432 -U heyake -d new_fs
```

### 5. Docker 命令

#### Docker 在 Mac OS 上
```bash
# Docker Desktop for Mac
# 下载：https://www.docker.com/products/docker-desktop

# 常用命令
docker-compose up -d
docker-compose logs -f
docker-compose ps
docker-compose down
```

## 数据迁移脚本适配

### MySQL 导出脚本 (Mac OS 适配)

```bash
#!/bin/bash
# export-data-mysql.sh
# MySQL 数据导出脚本 - Mac OS 版本

# 数据库连接信息
DB_HOST="localhost"
DB_PORT="3306"
DB_USER="root"
DB_PASS="iWhale@2023"
DB_NAME="fs_enabling"
OUTPUT_DIR="./data/backup/$(date +%Y%m%d)"

# 创建输出目录
mkdir -p "$OUTPUT_DIR"

# 导出数据
mysqldump -h "$DB_HOST" -P "$DB_PORT" \
  -u "$DB_USER" -p"$DB_PASS" \
  --single-transaction \
  --routines \
  --triggers \
  --databases "$DB_NAME" \
  > "$OUTPUT_DIR/fs_enabling_backup.sql"

echo "数据导出完成: $OUTPUT_DIR/fs_enabling_backup.sql"
```

### PostgreSQL 导入脚本 (Mac OS 适配)

```bash
#!/bin/bash
# import-data-postgres.sh
# PostgreSQL 数据导入脚本 - Mac OS 版本

# 数据库连接信息
DB_HOST="localhost"
DB_PORT="5432"
DB_USER="heyake"
DB_NAME="new_fs"
INPUT_FILE="./data/backup/fs_enabling_backup.sql"

# 创建数据库
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" \
  -c "DROP DATABASE IF EXISTS $DB_NAME;"

psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" \
  -c "CREATE DATABASE $DB_NAME;"

# 导入数据
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" \
  -f "$INPUT_FILE"

echo "数据导入完成"
```

## 开发环境配置

### Java 环境

```bash
# 检查 Java 版本
java -version

# 设置 JAVA_HOME (如果需要)
# 通常 Mac OS 的 Homebrew 安装会自动配置

# Maven 配置
cd backend
mvn clean install
mvn spring-boot:run
```

### Node.js 环境

```bash
# 检查 Node.js 版本
node -v

# 安装依赖 (frontend-pc)
cd frontend-pc
npm install

# 启动开发服务器
npm run dev
```

## 常见问题解决

### 1. MySQL 连接被拒绝

```bash
# 检查 MySQL 是否运行
ps aux | grep mysql

# 启动 MySQL
brew services start mysql

# 或使用 Docker
docker-compose up -d mysql
```

### 2. 端口被占用

```bash
# 查找占用端口的进程
lsof -i :3306
lsof -i :5432

# 杀死进程
kill -9 <PID>

# 或修改配置文件中的端口
vim backend/src/main/resources/application.yml
vim docker-compose.yml
```

### 3. 文件权限问题

```bash
# 修改脚本执行权限
chmod +x scripts/*.sh

# 修改目录权限
chmod -R 755 srcnew/
```

---

**创建日期**: 2026-01-24  
**适用系统**: Mac OS
