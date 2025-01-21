FROM openjdk:17-jdk

WORKDIR /app

COPY build/libs/foodDeliveryApp-0.0.1-SNAPSHOT.jar /app/food-delivery-app.jar

EXPOSE 8080

CMD ["java", "-jar", "food-delivery-app.jar"]