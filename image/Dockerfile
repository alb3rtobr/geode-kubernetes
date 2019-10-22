FROM alb3rtobr/mygeode:1.10.0

RUN apt-get update \
    && apt-get install -y curl wget net-tools telnet iputils-ping

ENV CLASSPATH /jars/metrics-publishing-service-1.0-SNAPSHOT.jar;/jars/micrometer-registry-prometheus-1.2.0.jar;/jars/simpleclient-0.6.0.jar

COPY gfshWrapper.sh gfshWrapper.sh
COPY metrics-publishing-service-1.0-SNAPSHOT.jar /jars/metrics-publishing-service-1.0-SNAPSHOT.jar
COPY micrometer-registry-prometheus-1.2.0.jar /jars/micrometer-registry-prometheus-1.2.0.jar
COPY simpleclient-0.6.0.jar /jars/simpleclient-0.6.0.jar
