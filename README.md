
#WIP...

## Modular Java, Embedded Tomcat, Fat Jar, Vue JS 
Application to demonstrate various parts of a service oriented RESTfull application.


### Technology Stack
Component          | Technology
---                | ---
Backend Lang       | Java 11 (with modules)
Restfull Framwork  | Jersey 
Container          | Tomcat 9 (Embeded Mode)
Backend Build Tool | Maven 3.5.4 (Creates a Executable Jar with tomcat embeded )
Security           | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt) )
REST Spec          | [Open API Standard](https://www.openapis.org/) 
In Memory DB       | H2 
Persistence        | JPA (Using Hibernate)
Frontend           | Vue JS
Client Build Tools | vue-cli, Webpack, yarn

## Prerequisites
Ensure you have this installed before proceeding further
- Java 11+
- Maven 3.5.4+


### Easy Build and Run 
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install

# Run The App 
java -jar ./web-api/target/modules/web-api-1.0.0.jar
```

##### Once the App is running
App Component       | URLs
---                 | ---
URL for API Docs    | http://localhost:8080/api-docs/index.html
URL to Access H2 DB | http://localhost:8082
URL for REST APIs   | http://localhost:8080/api

## Backers

Help me to host this app on AWS or Google-Cloud, for everyone to checkout the app. $5 will allow me to host for a month !!! 
[[Become a backer](https://opencollective.com/angular-springboot-rest-jwt#backer)]

<a href="https://opencollective.com/angular-springboot-rest-jwt#backers" target="_blank"><img src="https://opencollective.com/angular-springboot-rest-jwt/backers.svg?width=890"></a>


## Sponsors
Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/angular-springboot-rest-jwt#sponsor)]


### Screenshots
#### Login
<kbd>
    <img src="/screenshots/login.png?raw=true">
</kbd>

#### API Reference 
<kbd>
    <img src="/screenshots/api_reference.png?raw=true">
</kbd>