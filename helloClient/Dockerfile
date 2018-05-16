FROM 100.125.16.65:20202/hwcse/dockerhub-java:8-jre-alpine

WORKDIR /home/apps/
ADD target/demo-0.0.1-SNAPSHOT.jar .
ADD target/lib ./lib
ADD start.sh .

ENTRYPOINT ["sh", "/home/apps/start.sh"]