FROM azwickey/geode
MAINTAINER Adam Zwickey <azwickey@pivotal.io>

ADD target/geode-demo-0.0.1-SNAPSHOT.jar app.jar
VOLUME ["/home/data/"]
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Xmx512m", "-jar", "/app.jar"]
