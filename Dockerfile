FROM maven:3-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18-alpine
COPY --from=build /target/sales-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]