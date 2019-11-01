From tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/*.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]

#FROM openjdk:8-jdk-alpine
#EXPOSE 8080
#ADD target/*.jar app.jar
#ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
