# dds-wvv-backend

## swagger-ui
- [Local swagger-ui](http://localhost:8080/swagger-ui.html#/)
- [Heroku master-branch swagger-ui](https://dds-wvv-backend.herokuapp.com/swagger-ui.html#/)

## Lombok
This project uses [Project Lombok] (https://projectlombok.org/)
Please make sure you install the correct Lombok Plugin for your used IDE to get correct code completion.
- [intellij plugin] (https://projectlombok.org/setup/intellij)
- [eclipse plugin] (https://projectlombok.org/setup/eclipse)
- [other IDEs] (https://projectlombok.org/setup/)

## SpringBoot2
This project is build on the framework SpringBoot2 [documentation] (https://spring.io/projects/spring-boot)

## Requirements

For running the backen service you need:

- [JDK 1.8](https://openjdk.java.net/install/)
- [Maven 3](https://maven.apache.org)

The service can locally either be started via your IDE starting directly the Main class com.diconiumwvv.storesservice.StoresserviceApplication

Or build and start the service via mvn spring-boot plugin

- $ mvn clean install
- $ mvn spring-boot:run

# How to connect to commercetools api
- to connect the spring boot app to the commercetools api you need to set the following config inside the application.yml


````
ctp: 
  projectKey: ${ctp.projectKey}
  clientId: ${ctp.clientId}
  clientSecret: ${ctp.clientSecret}
  authUrl: ${ctp.authUrl}
  apiUrl: ${ctp.apiUrl}
````

# Build as Docker container

- $ mvn package -DskipTests
- $ docker build -t dds-wvv-backend .
