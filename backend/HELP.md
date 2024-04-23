# Getting Started

### Reference Documentation


### Build new docker image
* ./gradlew clean bootJar
* docker build -t haduin/gddkia-backend .
* docker push haduin/gddkia-backend


### Useful links for feature development
* [SpringSecurity Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#servlet-authentication-unpwd-storage)
* [SpringSecurity Authentication Events](https://docs.spring.io/spring-security/reference/servlet/authorization/events.html#authorization-granted-events)