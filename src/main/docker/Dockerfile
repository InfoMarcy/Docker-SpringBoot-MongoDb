FROM centos
RUN yum install -y java

VOLUME /tmp
ADD /luxmart-store-angular.jar myapp.jar
RUN sh -c 'touch /myapp.jar'
ENV JAVA_OPTS=""
ENV SERVER_PORT 8585

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongodb:27017","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]