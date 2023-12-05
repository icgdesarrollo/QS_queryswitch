FROM eclipse-temurin:19.0.1_10-jre-alpine
COPY /target/QuerySwitch-0.0.1-SNAPSHOT.jar /home/QuerySwitch-0.0.1-SNAPSHOT.jar
RUN mkdir -p /data/jks/
COPY /mnt/valcuenta.jks /data/jks/valcuenta.jks
CMD ["java","-jar","/home/QuerySwitch-0.0.1-SNAPSHOT.jar"]
