FROM eclipse-temurin:19.0.1_10-jre-alpine
COPY ./QuerySwitch/target/QuerySwitch-0.0.1-SNAPSHOT.jar /home/QuerySwitch-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","/home/QuerySwitch-0.0.1-SNAPSHOT.jar"]