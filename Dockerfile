# Etapa 1: Construcción de la app
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el código fuente y pom.xml
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el .jar
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con JAR ejecutable
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia el JAR generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto que usa tu app (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
