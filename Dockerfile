FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ENV PROFILE=prod
ENV PORT=8888
ENV MYSQL_URL=3.121.195.132
ENV MONGO_URL=3.121.195.132
COPY server ${JAR_FILE}
EXPOSE ${PORT}
ENTRYPOINT [
"java",
"-jar",
"${JAR_FILE}",
"--spring.profiles.active=${PROFILE}",
"--server.port=${PORT}",
"--mongo.url=${MONGO_URL}",
"--mysql.url=${MYSQL_URL}"
]