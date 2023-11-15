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

v0.0.6 Circuit Breaker Resilience4J

[Spring Cloud Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker)

[Resilience4J](https://github.com/resilience4j/resilience4j)

<details>
<summary><strong>Circuit Breaker описание</strong></summary>

Spring Cloud Circuit Breaker - это часть проекта Spring Cloud, предназначенная для обработки
ситуаций, когда одна из служб в вашем микросервисном приложении становится нестабильной или
недоступной. Это помогает в создании устойчивых и отказоустойчивых систем.

<p>Как это работает:</p>
<ol>
<li>
Circuit Breaker (Защитный выключатель): Circuit Breaker - это паттерн проектирования, который позволяет системе изолировать компонент или службу, когда она становится нестабильной. Вместо того, чтобы давать запросы к нестабильной службе, Circuit Breaker блокирует доступ к этой службе и предоставляет альтернативные данные или возвращает ошибку без вызова нестабильной службы.
</li>
<li>
Fallback (Резервный вариант): Если Circuit Breaker обнаруживает, что служба недоступна, он может использовать резервный вариант - заранее определенный ответ или логику, которая возвращает данные пользователю вместо результата вызова недоступной службы.
</li>
<li>
Мониторинг и восстановление: Circuit Breaker также обычно включает мониторинг. Если служба
восстанавливается и становится стабильной снова, Circuit Breaker может автоматически восстановить
доступ к ней.
</li>
</ol>

<p>Почему это важно:</p>
<lo>
<li>
Повышение устойчивости: Circuit Breaker предотвращает перегрузку нестабильных служб, предотвращая таким образом сбои в системе из-за одной нестабильной части.
</li>
<li>
Быстрое восстановление: Путем блокировки недоступной службы и использования резервных вариантов, система может продолжать работать даже при временных сбоях в других службах.
</li>
<li>
Предсказуемость поведения: Circuit Breaker предоставляет предсказуемое поведение при сбоях, позволяя приложению более гибко реагировать на проблемы с доступностью.
</li>
</lo>

<p>
Spring Cloud Circuit Breaker предоставляет абстрактный уровень для работы с различными реализациями Circuit Breaker, такими как Netflix Hystrix, Resilience4j и Spring Retry. Он упрощает конфигурацию и интеграцию Circuit Breaker в вашем микросервисном приложении, облегчая обработку ошибок и обеспечивая устойчивость системы.
</p>

</details>

[Examples of resilience4j](https://resilience4j.readme.io/docs/getting-started-3)

* для теста поднять ордер сервис на 8081
* http://localhost:8081/actuator/health
* остановить серсис инвентаризации
* сделать 5 запросов http://localhost:8080/api/order/
* resilience4j.timelimiter.instances.inventory.timeout-duration=3s
* устанавливает таймаут ожидание запроса
* resilience4j.retry.instances.inventory.max-attempts=3
* resilience4j.retry.instances.inventory.wait-duration=5s
* устанавливает количество попыток ретрая запроса интервал

проверить статистику
http://localhost:8081/actuator
http://localhost:8081/actuator/retryevents
http://localhost:8081/actuator/timelimiterevents

v0.0.7 Distributed Tracing Распределенная тарссировка

[Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)

[zipkin](https://zipkin.io/pages/quickstart.html)

`docker run -d -p 9411:9411 openzipkin/zipkin`

v0.0.8 Event Driven Architecture using Kafka

[Apache Kafka® Quick Start](https://developer.confluent.io/quickstart/kafka-local/)

[Быстрый старт для Confluent Platform](https://docs.confluent.io/platform/current/platform-quickstart.html#ce-docker-quickstart)

```properties
#Kafka Properties
spring.kafka.bootstrap-server=localhost:9092
spring.kafka.template.default-topic=notificationTopic
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.properties.spring.json.type.mapping=event:com.github.skiflok.orderservice.event.OrderPlacedEvent
```

```properties
#Kafka Properties
spring.kafka.bootstrap-server=localhost:9092
spring.kafka.template.default-topic=notificationTopic
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.properties.spring.json.type.mapping=event:com.github.skiflok.orderservice.event.OrderPlacedEvent
```

v0.0.9 Dockerized the application

```dockerfile
FROM openjdk:17

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

<details>
<summary><strong>Описание работы докерфайла</strong></summary>

Этот Dockerfile используется для создания Docker-образа для приложения на Java

<p>Как это работает:</p>
<ol>
<li>
FROM openjdk:17: Определяет базовый образ, который будет использован для создания нового образа. В данном случае используется образ с OpenJDK 17.
</li>
<li>
COPY target/*.jar app.jar: Копирует все JAR-файлы из каталога target (предположительно, это каталог, где обычно собираются Java-приложения с использованием Maven или Gradle) внутрь контейнера и называет скопированный файл app.jar.
</li>
<li>
ENTRYPOINT ["java", "-jar", "/app.jar"]: Устанавливает точку входа для контейнера. Когда контейнер будет запущен, он выполнит команду java -jar /app.jar, запуская тем самым Java-приложение из JAR-файла.
</li>
</ol>

<p>
Итак, этот Dockerfile создает контейнер, включающий в себя OpenJDK 17 и запускающий Java-приложение, указанное в app.jar с использованием команды java -jar.</p>

</details>

```shell
cd api-gateway
docker build -t api-gateway-dockerfile .
```

<details>
<summary><strong>Описание</strong></summary>

Команда docker build -t api-gateway-dockerfile . используется для построения Docker-образа с тегом api-gateway-dockerfile из текущего контекста сборки (текущего каталога, где находится Dockerfile).

<p>Как это работает:</p>
<ol>
<li>
docker build: Эта команда запускает процесс сборки Docker-образа.</li>
<li>
-t api-gateway-dockerfile: Опция -t используется для установки тега (имени) Docker-образа. В данном случае, тег установлен как api-gateway-dockerfile.</li>
<li>
.: Это означает текущий контекст сборки, то есть текущий рабочий каталог, где находится Dockerfile.</li>
</ol>

<p>
Таким образом, после выполнения этой команды Docker будет использовать Dockerfile, который находится в текущем каталоге, для построения образа и присвоит ему тег api-gateway-dockerfile.
</p>
</details>

[Multi-stage builds](https://docs.docker.com/build/building/multi-stage/)

```dockerfile
FROM eclipse-temurin:17.0.4.1_1-jdk as builder
WORKDIR extracted
ADD target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```

<details>
<summary><strong>Описание</strong></summary>

Этот Dockerfile используется для создания Docker-образа для приложения на основе Spring Boot

<p>Как это работает:</p>
<ol>
<p>Первый этап (builder):</p>
<li>
FROM eclipse-temurin:17.0.4.1_1-jdk as builder: Этот этап использует образ JDK от Eclipse Temurin (ранее известного как AdoptOpenJDK) версии 17 как базовый образ и называет этот этап "builder".
</li>
<li>
WORKDIR extracted: Устанавливает рабочий каталог внутри контейнера.
</li>
<li>
ADD target/*.jar app.jar: Копирует все JAR-файлы из каталога "target" внутрь образа и называет их "app.jar".
</li>

<li>
RUN java -Djarmode=layertools -jar app.jar extract: Выполняет команду для извлечения слоев JAR-файла. Это используется для разделения зависимостей, загрузчика Spring Boot и самого приложения в разные слои.
</li>
</ol>

<p>Второй этап:</p>

<ol>
<p>Первый этап (builder):</p>
<li>
FROM eclipse-temurin:17.0.4.1_1-jre: Использует образ JRE от Eclipse Temurin (второй этап).
</li>
<li>
WORKDIR application: Устанавливает рабочий каталог внутри контейнера.
</li>
<li>
COPY --from=builder extracted/dependencies/ ./: Копирует зависимости из слоя "dependencies" внутрь контейнера.
</li>

<li>
COPY --from=builder extracted/spring-boot-loader/ ./: Копирует загрузчик Spring Boot из слоя "spring-boot-loader".
</li>

<li>
COPY --from=builder extracted/snapshot-dependencies/ ./: Копирует снимок зависимостей из слоя "snapshot-dependencies".
</li>
<li>
COPY --from=builder extracted/application/ ./: Копирует само приложение из слоя "application".
</li>

<li>
EXPOSE 8080: Объявляет, что контейнер будет слушать порт 8080 (это просто метаинформация и не приводит к автоматическому открытию порта).
</li>

<li>
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]: Задает точку входа для контейнера, указывая использовать загрузчик JAR Spring Boot.
</li>

</ol>

<p>
Этот Dockerfile использует два этапа сборки. Первый этап использует JDK для сборки и извлечения слоев JAR, а второй этап использует JRE и копирует извлеченные слои JAR, формируя таким образом более оптимизированный образ Docker.
</p>
</details>

```shell
cd api-gateway
docker build -t api-gateway-layered -f Dockerfile.layered .
```

docker build используется для создания Docker-образа с именем api-gateway-layered
из Dockerfile с именем Dockerfile.layered. 

Опция -f позволяет указать путь к файлу Dockerfile.

**главное не забыть создать собсбтвенно jar**

[Building Java containers with Jib](https://cloud.google.com/java/getting-started/jib)

setting.xml file - Users\<your username>\.m2\settings.xml

