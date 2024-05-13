# First stage: Build the application
FROM maven:3.9-eclipse-temurin-19-alpine as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Create Image
FROM eclipse-temurin:19.0.1_10-jre-alpine
COPY --from=build /app/target/QuerySwitch-4.0.0-SNAPSHOT.jar /home/QuerySwitch-4.0.0-SNAPSHOT.jar
RUN mkdir -p /data2/jks/
COPY /mnt/valcuenta.jks /data2/jks/valcuenta.jks
CMD ["java","-jar","/home/QuerySwitch-4.0.0-SNAPSHOT.jar"]
