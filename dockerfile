# Usa una imagen de OpenJDK 21 como base
FROM openjdk:21-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY . /app

# Ejecutar Maven para que las dependencias se resuelvan antes de construir
RUN mvn dependency:go-offline

# Construir el proyecto
RUN mvn clean install

# Exponer el puerto que usará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/proxy-meli-0.0.1.jar"]
