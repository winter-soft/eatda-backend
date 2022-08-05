FROM scottyengineering/java11
VOLUME /tmp
ARG JAR_FILE=./build/libs/swhackathon-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]