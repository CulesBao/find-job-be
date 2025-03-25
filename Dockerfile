FROM amazoncorretto:23
WORKDIR app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} find-job-be.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","find-job-be.jar"]