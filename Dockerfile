FROM ubuntu:rolling

RUN apt-get update

RUN echo "locales locales/locales_to_be_generated multiselect en_US.UTF-8 UTF-8" | debconf-set-selections
RUN apt-get install -y locales
RUN locale-gen en_US.UTF-8

ENV DEBIAN_FRONTEND noninteractive
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US
ENV LC_ALL en_US.UTF-8

RUN apt install -y --no-install-recommends openjdk-11-jdk wget
RUN rm -rf /var/lib/apt/lists/*
RUN apt-get clean

RUN wget 'https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true' -O MockMock.jar

EXPOSE 8282/tcp
EXPOSE 25/tcp

ENTRYPOINT java -jar MockMock.jar
