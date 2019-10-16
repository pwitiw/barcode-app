FROM adoptopenjdk/openjdk11:latest

RUN apt update && apt install -y libxkbcommon-x11

# copy jar
COPY build/libs/reader-0.0.1-SNAPSHOT.jar .

# and away we go
CMD java -jar -Dspring.profiles.active=docker reader-0.0.1-SNAPSHOT.jar
