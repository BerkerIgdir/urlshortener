FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xdebug","-Xrunjdwp:transport=dt_socket,address=*:8080,server=y,suspend=y","-jar","/app.jar"]
