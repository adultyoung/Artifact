<p align="center"><img width="200" src="https://i.ibb.co/XCy1fYt/logo.png" alt="Artifact logo"></p>
<h1 align="center">Artifact</h1>

Artifact ��� �������������� ������� � Java + Spring Boot �� �������, Node.js + Vue.js �� ��������� � PostgreSQL ��� ��. ��� ������� ������� ��������� ��� ������ Websocket + SockJs � hot reload. �������������� �������� � ������� OAuth2 ����� Google � ��� ������ JWT ������ � CORS � CSRF �������.

## ���� ����������
���������         | ����������
---               | ---
Frontend          | [Vue.js 2](https://github.com/vuejs/vue)
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security, [JJWT](https://github.com/auth0/java-jwt), CSRF)
Client Build Tools| [vue-cli](https://github.com/vuejs/vue-cli), [yarn](https://github.com/yarnpkg/yarn)
Server Build Tools| Gradle

## Installation
#### Import project (_Intellij IDEA_)

1. `File` -> `New` -> `Project From Existing Source` -> ������� ���� � �������;
2. `CTRL + SHIFT + ALT + S` -> ` Modules` -> `+` -> ` Import Module` -> `�������� frontend �����, ������ 'Create module from existing sources'`;

### ������ �������

`gradle Build` : ��������� � �������� �����  `build.gradle` � ���������:

```sbtshell
gradle bootRun
```
��� ��������� �� IDE ����� `ArtifactApplication.java`.

_���������� ����� �������� �� [http://localhost:8091](http://localhost:8091)._


### ������ ���������

��������� � �������� ����� `frontend` (������ ��������� `package.json`)

```npm
yarn install

yarn serve
```
_���������� ����� �������� ��  [http://localhost:8080](http://localhost:8080)._

#### �������� �������������:
```
Admin - admin:password
User - user:password
```