FROM eclipse-temurin:17-jre

WORKDIR /app

COPY ["./target/order-service-0.0.1-SNAPSHOT.jar", "."]

EXPOSE 8090

CMD java -jar order-service-0.0.1-SNAPSHOT.jar