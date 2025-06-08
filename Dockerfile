FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/clinifono-0.0.1-SNAPSHOT.war

COPY tomcat/conf/server.xml /usr/local/tomcat/conf/server.xml

COPY certificado.jks /etc/ssl/CA/certificado.jks

EXPOSE 8443 8080

CMD ["catalina.sh", "run"]