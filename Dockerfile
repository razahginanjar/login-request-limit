#Build stage
FROM maven:3.8.7-openjdk-18 as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime Stage
FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=0.0.1

#define few things
WORKDIR /app
COPY --from=build /build/target/Login-*.jar /app/

EXPOSE 8181

ENV DB_URL=jdbc:postgresql://postgres_login_redis:5432/login_redis
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} Login-${JAR_VERSION}-SNAPSHOT.jar


