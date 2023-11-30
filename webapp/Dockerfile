FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /home/app/ /home/app/
RUN mv /home/app/target/webapp-0.0.1-SNAPSHOT.jar /home/app/
ENTRYPOINT ["java","-jar","webapp-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]