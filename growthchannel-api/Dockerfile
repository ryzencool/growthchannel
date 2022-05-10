from maven:3.6.3-adoptopenjdk-11 as stage1

run mkdir -p /app/source

copy . /app/source

workdir /app/source


RUN mvn clean package -Dmaven.test.skip=true

from adoptopenjdk/openjdk11:jre-11.0.9_11-alpine as stage2

copy --from=stage1 /app/source/target/*.jar /app/app.jar

expose 8080

entrypoint ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]