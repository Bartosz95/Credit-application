FROM openjdk:11
ADD target/customer.jar customer.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "customer.jar"]