# 商品管理中心 Dockerfile
# 多阶段构建

# ============ 第一阶段：构建 ============
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# 复制 pom.xml 并下载依赖（利用缓存）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码并构建
COPY src ./src
RUN mvn clean package -DskipTests -B

# ============ 第二阶段：运行 ============
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="admin@example.com"
LABEL description="商品管理中心"

WORKDIR /app

# 创建非 root 用户
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 创建上传目录
RUN mkdir -p /app/upload && chown -R appuser:appgroup /app

# 从构建阶段复制 jar 包
COPY --from=builder /app/target/*.jar app.jar

# 设置时区
ENV TZ=Asia/Shanghai
RUN apk add --no-cache tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

# 切换到非 root 用户
USER appuser

# 暴露端口
EXPOSE 8080

# JVM 参数
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget -q --spider http://localhost:8080/login || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
