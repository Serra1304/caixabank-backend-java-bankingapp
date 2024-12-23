## Imagen base de java
#FROM openjdk:21-jdk-slim
#
## Se establece directorio de trabajo dentro del contenedor
#WORKDIR /app
#
## Se el archivo pom.xml y las fuentes del proyecto
#COPY pom.xml /app/pom.xml
#COPY src /app/src
#
## Instalar Maven para construir el proyecto (sin utilizar /target)
#RUN apt-get update && \
#    apt-get install -y maven && \
#    mvn -f /app/pom.xml clean package && \
#    apt-get remove -y maven && \
#    apt-get autoremove -y && \
#    rm -rf /var/lib/apt/lists/*
#
## Puerto en el que corre la aplicación
#EXPOSE 3000
#
## Ejecutar el JAR de la aplicación
#CMD ["java", "-jar", "target/finservice-0.0.1-SNAPSHOT.jar"]

# Java base image
FROM openjdk:21-jdk-slim

# Etapa de compilación
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline -B

COPY src /app/src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/finservice-0.0.1-SNAPSHOT.jar /app/finservice-0.0.1-SNAPSHOT.jar

EXPOSE 3000

CMD ["java", "-jar", "finservice-0.0.1-SNAPSHOT.jar"]
