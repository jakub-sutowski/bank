FROM openjdk:17

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

## Etap 1: Użyj obrazu Maven do zbudowania aplikacji
#FROM maven:3.8.4-openjdk-17-slim AS build
#
## Ustawienie katalogu roboczego
#WORKDIR /app1
#
## Skopiuj pliki pom.xml i źródłowe
#COPY pom.xml .
#COPY src ./src
#
## Zbuduj aplikacje
#RUN mvn clean package
#
## Etap 2: Utwórz końcowy obraz z obrazu Java
#FROM openjdk:17-slim
#
## Ustawienie katalogu roboczego
#WORKDIR /app1
#
## Skopiuj pliki JAR z etapu budowania
#COPY --from=build /app/target/*.jar app.jar
#
## Eksponuj odpowiedni port
#EXPOSE 8080
#
## Uruchom aplikację
#CMD ["java", "-jar", "app.jar"]