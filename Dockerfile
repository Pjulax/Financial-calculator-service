FROM openjdk:11-jdk
EXPOSE 8080
COPY ${JAR_FILE} ${JAR_FILE}
ADD target/fin-calc-service-*.jar fin-calc-service.jar
ENTRYPOINT ["java","-jar","/fin-calc-service.jar"]
