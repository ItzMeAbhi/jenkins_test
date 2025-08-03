FROM openjdk:17

COPY target/jenkins-cicd-test-1.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]