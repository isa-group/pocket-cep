FROM java:8

ENV api_key none

#WORKDIR /usr/src/pocket-cep

ADD target/Pocket-CEP-0.8.jar Pocket-CEP.jar

EXPOSE 9999

CMD ["java", "-Dspring.profiles.active=${api_key}", "-jar", "Pocket-CEP.jar"]
