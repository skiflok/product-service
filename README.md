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
* добавлен discovery service на базе netflix-eureka-server. позволяет службам находить друг друга и взаимодействовать друг с другом без жесткого кодирования имени хоста и порта