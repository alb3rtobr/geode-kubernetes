FROM alb3rtobr/mygeode:1.10.0
LABEL maintainer="alberto.bustamante.reyes@est.tech"

ADD target/geode-demo-0.0.1-SNAPSHOT.jar app.jar
VOLUME ["/home/data/"]
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Xmx512m", "-jar", "/app.jar"]
