# Sử dụng OpenJDK 17 làm base image
FROM openjdk:21

# Đặt biến môi trường để giảm thời gian khởi động Spring Boot
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR vào container (Giả sử JAR được build tại target/*.jar)
COPY target/*.jar app.jar

# Chạy ứng dụng Spring Boot
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
