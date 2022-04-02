## Modular Java, Embedded Tomcat, Fat Jar, Vue JS 
Application to demonstrate various parts of a service oriented RESTfull application.

## Demo (Heroku Hosted)
Allow about 2-3 mins for the instance to start  
- [WebApp](https://modular-java-jersey-vue.herokuapp.com)
- [API Reference (Open API Spec)](https://modular-java-jersey-vue.herokuapp.com/api-docs/index.html)

### Technology Stack
Component          | Technology
---                | ---
Backend Lang       | Java 11 (with modules)
Restfull Framework  | Jersey 
Container          | Tomcat 9 (Embeded Mode)
Server Build Tools | maven 3.5.4 (Creates a Executable Jar with embeded tomcat)
Security           | Token Based ([JWT](https://github.com/auth0/java-jwt) )
REST Spec          | [Open API Standard](https://www.openapis.org/) 
In Memory DB       | H2 
Persistence        | JPA (Using Hibernate)
Frontend           | Vue JS
Client Build Tools | vue-cli, Webpack, yarn

## Prerequisites
Ensure you have this installed before proceeding further
- Java 11+ 
- Maven 3.5.4+
- yarn 1.10.1 
- node 10.12.0
- vue-cli 3.0.5

## Folder Structure
```bash
PROJECT_FOLDER
│  README.md
│  pom.xml       # Parent maven project (contains other sub projects)
│
└──[database]    # Java-Project/Java-Module (contains H2 database related services )  
│  │  pom.xml       
│  └──[src]      
│     └──[main]      
│        └──[java]         # java source files   
│        └──[resources]
│              schema.sql  # Contains sql script to generate database tables and views in H2
│              data.sql    # Contains sql script to fill the tables with sample data
│
└──[web-api]     # Java-Project/Java-Module ( The Main WebApp contating RESTfull APIs )  
│  │  pom.xml      
│  └──[src]      
│     └──[main]      
│        └──[java]        # java source files   
│        └──[resources]
│        └──[webapp]      # files/folders under webapp is accessible from web-browser
│           └──[ui]       # maven build script would copy web-ui/dist into this folder, to make UI available from the browser
│           └──[api-docs] # contains swagger-ui source for API documentation and try-out
│
└──[web-ui]     # A regular folder that contains VueJS based UI source code 
│  │  package.json  
│  │  vue.config.js  
│  │  package.json   
│  └──[node_modules] # files under this is downloaded by 'yarn install' command       
│  └──[dist]         # VueJs source is compiled, bundled and minified into this folder 
│  └──[src]          # contains ui source code
```

### Build Process 
- 1st Build Frontend (optional, required only if you make changes to frontend code )
- Then Build Backend (backend build script will copy UI code build above into itself)  

#### To build frontend (optional step)
```bash
# run these commands from web-ui folder

yarn install
yarn build

```

#### To build backend
```bash
# run these commands from root folder where top-level pom.xml is present

# build the web app
mvn clean install

# Run The App 
java -jar ./web-api/target/modules/web-api-1.0.0.jar
```

##### Once the App is running
App Component        | URLs
---                  | ---
URL for the web app  | http://localhost:8080 or http://localhost:8080/ui/index.html
URL for API Docs     | http://localhost:8080/api-docs/index.html
BaseURL for REST APIs| http://localhost:8080/api


### Screenshots
#### Login
<kbd>
    <img src="/screenshots/login.png?raw=true">
</kbd>

#### Dashboard
<kbd>
    <img src="/screenshots/dashboard.png?raw=true">
</kbd>

#### API Reference 
<kbd>
    <img src="/screenshots/api_reference.png?raw=true">
</kbd>

## Backers
Help me to host this app on AWS or Google-Cloud, for everyone to checkout the app.
[[Become a backer](https://opencollective.com/angular-springboot-rest-jwt#backer)]

<a href="https://opencollective.com/angular-springboot-rest-jwt#backers" target="_blank"><img src="https://opencollective.com/angular-springboot-rest-jwt/backers.svg?width=890"></a>


## Sponsors
Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/angular-springboot-rest-jwt#sponsor)]


