FROM maven:3.8.4-jdk-11-slim

COPY src /app/src
COPY pom.xml /app/pom.xml
COPY populate.sh /app/populate.sh
WORKDIR /app

RUN mvn clean compile dependency:go-offline -DskipTests

CMD ["mvn", "exec:java", "-Dexec.mainClass=pl.projectfiveg.Application"]
