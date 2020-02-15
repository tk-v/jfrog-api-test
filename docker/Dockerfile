FROM jfrog.local:5001/ubuntu:latest

RUN /bin/bash -c cd ~; cd /home ; mkdir -p exec
ADD jdk/jdk-8-linux-x64.tar.gz /home/exec
RUN /bin/bash -c cd /home/exec; mv /home/exec/jdk* /home/exec/jdk8
ENV JAVA_HOME=/home/exec/jdk8
RUN sed "/securerandom.source=/{s/file:\/dev\/random/file:\/dev\/urandom/}" /home/exec/jdk8/jre/lib/security/java.security -i
ADD jar/jfrog-api-test.jar /home/exec/jfrog-api-test.jar

