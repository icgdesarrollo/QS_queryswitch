FROM eclipse-temurin:19.0.1_10-jre-alpine
COPY /target/QuerySwitch-4.0.0-SNAPSHOT.jar /home/QuerySwitch-4.0.0-SNAPSHOT.jar
RUN mkdir -p /data2/jks/
COPY /mnt/valcuenta.jks /data2/jks/valcuenta.jks
CMD ["java","-jar","/home/QuerySwitch-4.0.0-SNAPSHOT.jar"]
