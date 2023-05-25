FROM eclipse-temurin:19.0.1_10-jre-alpine
COPY /target/QuerySwitch-0.0.1-SNAPSHOT.jar /home/QuerySwitch-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/home/QuerySwitch-0.0.1-SNAPSHOT.jar"]
