<p align="center"><img width="200" src="https://i.ibb.co/XCy1fYt/logo.png" alt="Artifact logo"></p>
<h1 align="center">Artifact</h1>

Artifact is a single page website with Java + Spring Boot on backend, Node.js + Vue.js on frontend and PostgreSQL as a DB. Both sides of project connected via Websocket + SockJs with hot reload. Authentication is possible with OAuth2 via Google and manual with JWT token with CORS and CSRF security.

## Стек Технологий
Компонент         | Технология
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

### Запуск бэкэнда

`gradle Build` : Перейдите в корневую папку  `build.gradle` и выполните:

```sbtshell
gradle bootRun
```
или запустите из IDE класс `ArtifactApplication.java`.

_Приложение будет запущено на [http://localhost:8091](http://localhost:8091)._


### Запуск фронтэнда

Перейдите в корневую папку `frontend` (должен содержать `package.json`)

```npm
yarn install

yarn serve
```
_Приложение будет запущено на  [http://localhost:8080](http://localhost:8080)._

#### Аккаунты пользователей:
```
Admin - admin:password
User - user:password
```
