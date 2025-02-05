FROM openjdk:21
ENV JAVA_OPTS="-Xms256m -Xmx512m"
WORKDIR /app
COPY target/*.jar app.jar
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]