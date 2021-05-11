FROM openjdk:11-jdk
RUN addgroup --system nodemonitor && adduser --system --group nodemonitor
USER nodemonitor:nodemonitor
COPY /target/*.jar /usr/src/nodemonitor.jar
WORKDIR /usr/src
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=k8s", "nodemonitor.jar"]
EXPOSE 8080/tcp