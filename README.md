# product-service

REST APP

v0.0.1 init project

* Модели, репозитории и контроллер для Product
* dto response and request
* Docker compose для MongoDB

v0.0.2 tests

* [Testcontainers](https://testcontainers.com/guides/getting-started-with-testcontainers-for-java/)
  для тестов

v0.0.3 order service

v0.0.4 inventory service discovery service

* добавлен сервер инвентаризации, осуществляющий проверку наличия товара на складе
* добавлен discovery service на базе netflix-eureka-server. позволяет службам находить друг друга и
  взаимодействовать друг с другом без жесткого кодирования имени хоста и порта

v0.0.5 api-gateway

* [Устаревшая проблема Spring Security](https://stackoverflow.com/questions/76339307/spring-security-deprecated-issue)
* [Get started with Keycloak on Docker](https://www.keycloak.org/getting-started/getting-started-docker)
* `docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev`
* spring-boot-microservices-realm
* Create client
* spring-cloud-client
* Client authentication ON
* [x] Service accounts roles
* [ ] Standard flow
* [ ] Direct access grants
* Realm Setting -> openid-configuration
*

spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8181/realms/spring-boot-microservices-realm

postman authorization

* authorization -> Type Oauth 2.0
* Grant Type - Client Credentials
* Access Token URL
  -> http://localhost:8181/realms/spring-boot-microservices-realm/.well-known/openid-configuration
  ->
  "
  token_endpoint":"http://localhost:8181/realms/spring-boot-microservices-realm/protocol/openid-connect/token"
* Client ID -> spring-cloud-client
* Client Secret -> Clients -> spring-cloud-client -> Credentials -> Client secret

postman test

create product
POST http://localhost:8080/api/product

```json
{
  "name": "Iphone 14",
  "description": "iphone 14",
  "price": 1400
}
```

GET http://localhost:8080/api/product

mongo DB http://localhost:28081/

POST http://localhost:8080/api/order/

```json
{
  "orderLinesItemsDtoList": [
    {
      "scuCode": "iphone_13",
      "price": "1200",
      "quantity": 1
    },
    {
      "scuCode": "iphone_13_red",
      "price": "1200",
      "quantity": 2
    }
  ]
}
```

доступ к eureka
http://localhost:8080/eyreka/web
http://localhost:8761/