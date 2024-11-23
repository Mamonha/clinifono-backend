FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim AS app
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN apt-get update && apt-get install -y nginx && \
    rm -rf /var/lib/apt/lists/*

COPY nginx/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80 8080

CMD service nginx start && java -jar app.jar