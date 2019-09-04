<p align="center"><img width="200" src="https://i.ibb.co/XCy1fYt/logo.png" alt="Artifact logo"></p>
<h1 align="center">Artifact</h1>

Artifact это одностраничный вебсайт с Java + Spring Boot на бэкэнде, Node.js + Vue.js на фронтэнде и PostgreSQL как БД. Обе стороны проекта соеденены при помощи Websocket + SockJs с hot reload. Аутентификация возможна с помощью OAuth2 через Google и при помощи JWT токена с CORS и CSRF защитой.

## Стек Технологий
Компонент         | Технология
---               | ---
Frontend          | [Vue.js 2](https://github.com/vuejs/vue)
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security, [JJWT](https://github.com/auth0/java-jwt), CSRF)
Client Build Tools| [vue-cli](https://github.com/vuejs/vue-cli), [yarn](https://github.com/yarnpkg/yarn)
Server Build Tools| Gradle

## Установка
#### Импорт проекта (_Intellij IDEA_)

1. `File` -> `New` -> `Project From Existing Source` -> укажите путь к проекту;
2. `CTRL + SHIFT + ALT + S` -> ` Modules` -> `+` -> ` Import Module` -> `Выберите frontend папку, указав 'Create module from existing sources'`;

### Запуск бэкэнда

Для начала вам нужно создать БД и назвать ее "artifact". Логин, пароль и путь к БД находяться в файле properties.

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
