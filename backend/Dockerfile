#Build
# Build stage
FROM maven:3.8.4-openjdk-17 as build

WORKDIR /app

# Копируем pom.xml и устанавливаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем JAR файл
COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]