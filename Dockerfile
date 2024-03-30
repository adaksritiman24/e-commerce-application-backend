FROM gradle:jdk21

EXPOSE 8080

WORKDIR /app
COPY build/libs/e-commerce-application.jar e-commerce-application.jar

CMD ["java","-jar", "e-commerce-application.jar"]
