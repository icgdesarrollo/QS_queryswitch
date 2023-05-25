FROM eclipse-temurin:19.0.1_10-jre-alpine
COPY /target/compromised-accounts-0.0.1-SNAPSHOT.jar /home/compromised-accounts-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/home/compromised-accounts-0.0.1-SNAPSHOT.jar"]
