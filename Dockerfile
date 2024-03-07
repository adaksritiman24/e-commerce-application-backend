FROM gradle:jdk21

EXPOSE 8080

WORKDIR /app
COPY . /app

CMD ["gradle","bootRun"]
