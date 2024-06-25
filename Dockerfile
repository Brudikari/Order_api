# Stage 1: Compilation avec Maven
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app

# Copier les fichiers de configuration Maven nécessaires
COPY pom.xml .
COPY src/ /app/src/

# Définir les options JVM pour limiter la mémoire
ENV MAVEN_OPTS="-Xmx2048m"

# Télécharger les dépendances et compiler le projet
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

# Stage 2: Image légère pour l'exécution
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/customers-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
