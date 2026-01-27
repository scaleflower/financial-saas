pipeline {
    agent any
    
    tools {
        maven 'Maven 3.9.11'
        jdk 'Java 17'
    }
    
    environment {
        APP_NAME = "financial-reimbursement-saas"
        PROJECT_DIR = "/opt/financial-saas"
        BACKUP_DIR = "/opt/financial-saas/backups"
        DEPLOY_USER = "root"
        DEPLOY_HOST = "localhost"
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }
    
    stages {
        stage('代码检出') {
            steps {
                echo '📥 检出代码...'
                dir(env.PROJECT_DIR) {
                    sh 'git fetch origin'
                    sh 'git reset --hard origin/main'
                    sh 'git pull origin main'
                }
            }
        }
        
        stage('代码检查') {
            steps {
                echo '🔍 SonarQube 代码质量分析...'
                dir(env.PROJECT_DIR) {
                    // 跳过测试编译
                    sh 'mvn clean compile -DskipTests'
                    // SonarQube 扫描 (需要配置SonarQube服务器)
                    // sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000'
                }
            }
        }
        
        stage('单元测试') {
            steps {
                echo '🧪 运行单元测试...'
                dir(env.PROJECT_DIR) {
                    sh 'mvn test -DskipTests=false || true'
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('构建打包') {
            steps {
                echo '📦 Maven 打包...'
                dir(env.PROJECT_DIR) {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('备份旧版本') {
            when {
                anyOf {
                    branch 'main'
                }
            }
            steps {
                echo '💾 备份当前运行版本...'
                sh """
                    mkdir -p ${BACKUP_DIR}
                    tar -czf ${BACKUP_DIR}/backup-\$(date +%Y%m%d-%H%M%S).tar.gz \\
                      -C ${PROJECT_DIR} services/* || true
                    ls -lh ${BACKUP_DIR}/ | tail -5
                """
            }
        }
        
        stage('停止服务') {
            steps {
                echo '⏹️  停止微服务...'
                script {
                    def services = ['tenant-service:8080', 'user-service:8762', 
                                   'org-service:8763', 'trans-service:8764',
                                   'loan-service:8765', 'repayment-service:8766',
                                   'approval-service:8767', 'notification-service:8768',
                                   'report-service:8769', 'file-service:8770']
                    services.each { srv ->
                        def parts = srv.split(':')
                        def name = parts[0]
                        def port = parts[1]
                        sh """
                            pid=\$(ss -tlnp | grep ':${port}' | grep -oP 'pid=\\K[0-9]+') || true
                            if [ -n \"\$pid\" ]; then
                                echo \"Stopping ${name} (pid: \$pid)\"
                                kill \$pid || true
                                sleep 2
                            fi
                        """
                    }
                }
            }
        }
        
        stage('部署服务') {
            steps {
                echo '🚀 部署微服务...'
                script {
                    def services = ['tenant-service', 'user-service', 'org-service', 
                                   'trans-service', 'loan-service', 'repayment-service',
                                   'approval-service', 'notification-service', 
                                   'report-service', 'file-service']
                    parallel(
                        "部署核心服务": {
                            sh """
                                cd ${PROJECT_DIR}/services/tenant-service
                                nohup mvn spring-boot:run > /tmp/tenant-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/user-service
                                nohup mvn spring-boot:run > /tmp/user-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/org-service
                                nohup mvn spring-boot:run > /tmp/org-service.log 2>&1 &
                            """
                        },
                        "部署业务服务": {
                            sh """
                                cd ${PROJECT_DIR}/services/trans-service
                                nohup mvn spring-boot:run > /tmp/trans-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/loan-service
                                nohup mvn spring-boot:run > /tmp/loan-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/repayment-service
                                nohup mvn spring-boot:run > /tmp/repayment-service.log 2>&1 &
                            """
                        },
                        "部署扩展服务": {
                            sh """
                                cd ${PROJECT_DIR}/services/approval-service
                                nohup mvn spring-boot:run > /tmp/approval-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/notification-service
                                nohup mvn spring-boot:run > /tmp/notification-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/report-service
                                nohup mvn spring-boot:run > /tmp/report-service.log 2>&1 &
                                cd ${PROJECT_DIR}/services/file-service
                                nohup mvn spring-boot:run > /tmp/file-service.log 2>&1 &
                            """
                        },
                        failFast: false
                    )
                }
            }
        }
        
        stage('健康检查') {
            steps {
                echo '🏥 服务健康检查...'
                script {
                    sleep 60
                    def endpoints = [
                        '8080:/tenants',
                        '8762:/users?tenantId=1',
                        '8763:/orgs/tree?tenantId=1',
                        '8764:/trans',
                        '8765:/loans?tenantId=1',
                        '8768:/notification/health',
                        '8769:/report/health',
                        '8770:/file/health'
                    ]
                    def failed = []
                    endpoints.each { endpoint ->
                        def parts = endpoint.split(':')
                        def port = parts[0]
                        def path = parts[1]
                        def response = sh(
                            script: "curl -s -o /dev/null -w '%{http_code}' http://localhost:${port}${path}",
                            returnStdout: true
                        ).trim()
                        if (response != '200' && response != '401') {
                            failed.add("端口${port}: ${response}")
                        }
                    }
                    if (failed.size() > 0) {
                        error "健康检查失败: ${failed.join(', ')}"
                    }
                }
            }
        }
        
        stage('提交代码') {
            when {
                branch 'main'
            }
            steps {
                echo '📤 提交代码到GitHub...'
                dir(env.PROJECT_DIR) {
                    sh 'git add -A'
                    sh 'git commit -m "ci: Jenkins自动部署 [skip ci]" || echo "无更改需要提交"'
                    sh 'git push origin main'
                }
            }
        }
    }
    
    post {
        success {
            echo '✅ 流水线执行成功！'
            emailext (
                subject: "✅ ${env.APP_NAME} 构建成功",
                body: "构建成功！\n构建号: ${env.BUILD_NUMBER}\n时间: ${new Date()}",
                to: "Scalesflowers@gmail.com"
            )
        }
        failure {
            echo '❌ 流水线执行失败！'
            emailext (
                subject: "❌ ${env.APP_NAME} 构建失败",
                body: "构建失败！\n构建号: ${env.BUILD_NUMBER}\n时间: ${new Date()}",
                to: "Scalesflowers@gmail.com"
            )
        }
        always {
            cleanWs()
        }
    }
}
