FROM gradle:jdk17

EXPOSE 8080

WORKDIR /app
COPY . /app

CMD ["gradle","bootRun"]
