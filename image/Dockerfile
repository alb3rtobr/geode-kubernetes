FROM alb3rtobr/mygeode:1.10.0

RUN apt-get update \
    && apt-get install -y curl wget net-tools telnet iputils-ping

COPY gfshWrapper.sh gfshWrapper.sh
COPY metrics-publishing-service-1.0-SNAPSHOT.jar /jars/metrics-publishing-service-1.0-SNAPSHOT.jar
