<p align="center"><img width="200" src="https://i.ibb.co/XCy1fYt/logo.png" alt="Artifact logo"></p>
<h1 align="center">Artifact</h1>

Artifact is a single page website with Java + Spring Boot on backend, Node.js + Vue.js on frontend and PostgreSQL as a DB. Both sides of project connected via Websocket + SockJs with hot reload. Authentication is possible with OAuth2 via Google and manual with JWT token with CORS and CSRF security.

## Technology stack
Component         | Technology
---               | ---
Frontend          | [Vue.js 2](https://github.com/vuejs/vue)
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security, [JJWT](https://github.com/auth0/java-jwt), CSRF)
Client Build Tools| [vue-cli](https://github.com/vuejs/vue-cli), [yarn](https://github.com/yarnpkg/yarn)
Server Build Tools| Gradle

## Installation
#### Import project (_Intellij IDEA_)

1. `File` -> `New` -> `Project From Existing Source` -> select directory 'backend' in the project;
2. `CTRL + SHIFT + ALT + S` -> ` Modules` -> `+` -> ` Import Module` -> `Choose frontend directory and 'Create module from existing sources'`;

### Backend start

First you need to create db with name "artifact" in postgreSQL. Login, password and path to db are in properties file.

`gradle Build` : go to root dir with  `build.gradle` and run:

```sbtshell
gradle bootRun
```
or just run `ArtifactApplication.java`.

_Application will start on [http://localhost:8091](http://localhost:8091)._


### Frontend start

Go to root dir of `frontend` (must include `package.json`)

In terminal run:

```npm
yarn install

yarn serve
```
_Application will start on  [http://localhost:8080](http://localhost:8080)._

#### User accounts:
```
Admin - admin:password
User - user:password
```
