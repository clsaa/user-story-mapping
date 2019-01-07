FROM java:8
ADD njuqa-server-1.0-SNAPSHOT.jar njuqa-server.jar
RUN sh -c 'touch /njuqa-server.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java -Xmx64M $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /njuqa-server.jar " ]