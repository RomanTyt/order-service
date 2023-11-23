# Order Service

Order Service - проект, построенный на Spring Boot. Проект предоставляет простую реализацию системы управления заказами с использованием Spring Data JPA, Spring Web и других технологий.
## Требования

- Java 17
- Maven 3.x

## Установка

1. Склонируйте репозиторий:

    ```bash
    git clone https://github.com/RomanTyt/order-service.git
    cd order-service
    ```

2. Соберите проект с помощью Maven:

    ```bash
    mvn clean install
    ```

3. Запустите приложение:

    ```bash
    java -jar target\order-service-0.0.1-SNAPSHOT.war
    ```

Приложение будет доступно по адресу [http://localhost:8282](http://localhost:8282).

## В проекте подключены (автоматически подтягиваются):

* _H2_ - легковесная база данных Java с открытым исходным кодом
* _Liquibase_ - автоматическое развертывание таблиц, необходимых для начала работы
* _Swagger_ - автоматически генерирует спецификацию проекта

## Использование

Проект предоставляет REST API для управления заказами. Документация API доступна по адресу [http://localhost:8282/swagger-ui/index.html#/](http://localhost:8282/swagger-ui/index.html#/).

## Тестирование

Для запуска тестов используйте следующую команду:

```bash
mvn test




