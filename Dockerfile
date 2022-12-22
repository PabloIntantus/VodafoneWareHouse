FROM openjdk:17-jdk-slim
ADD target/vodafone-warehouse.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]