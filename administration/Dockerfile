FROM adoptopenjdk/openjdk11
COPY ./build/libs/administration-1.0.3.jar /app/administration.jar
ENTRYPOINT ["java"]
CMD ["-jar", "-Dspring.profiles.active=docker", "/app/administration.jar"]
EXPOSE 8888
#RUN apk --update --no-cache add curl
HEALTHCHECK CMD curl -f http://localhost:8888 || exit 1