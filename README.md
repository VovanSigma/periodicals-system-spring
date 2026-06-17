# Система Періодичні видання

Лабораторна робота 2 з ООП Java, 3 курс.

## Технології

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- OAuth2 Resource Server JWT
- Keycloak або Auth0 для JWT
- Liquibase
- PostgreSQL
- Lombok
- MapStruct
- Gradle

## Логіка системи

Адміністратор веде каталог періодичних видань. Читач переглядає список видань, вибирає потрібні позиції та оформлює передплату. Система автоматично рахує суму до оплати за формулою:

```text
сума = сума цін вибраних видань за місяць * кількість місяців
```

Після цього читач реєструє платіж. Після платежу статус передплати змінюється з CREATED на PAID.

## Таблиці

У проєкті використано 5 таблиць:

1. app_users
2. periodicals
3. subscriptions
4. subscription_items
5. payments

## Запуск бази

Створити базу PostgreSQL:

```sql
CREATE DATABASE periodicals_spring_db;
```

Пароль до PostgreSQL вказується у файлі:

```text
src/main/resources/application.properties
```

За замовчуванням:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/periodicals_spring_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Liquibase сам створить таблиці та додасть тестові дані при першому запуску.

## Запуск backend

```cmd
gradlew.bat bootRun
```

Адреса backend:

```text
http://localhost:8081/periodicals-spring
```

## REST API

### Отримати каталог

```http
GET http://localhost:8081/periodicals-spring/api/periodicals
```

### Створити передплату

```http
POST http://localhost:8081/periodicals-spring/api/subscriptions
Content-Type: application/json
```

```json
{
  "userId": 2,
  "periodicalIds": [1, 2],
  "months": 3
}
```

Очікувана сума:

```text
(120 + 150) * 3 = 810 грн
```

### Зареєструвати платіж

```http
POST http://localhost:8081/periodicals-spring/api/payments
Content-Type: application/json
```

```json
{
  "subscriptionId": 1,
  "method": "CARD"
}
```

## Spring Security і JWT

У проєкті є конфігурація Spring Security як OAuth2 Resource Server для JWT-токенів Keycloak або Auth0.

Для простого локального тестування без окремого Keycloak використано режим:

```properties
app.security.enabled=false
```

Для запуску з Keycloak треба змінити:

```properties
app.security.enabled=true
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/periodicals
```

У коді передбачені ролі:

- ADMIN - керування каталогом
- READER - оформлення передплати та платежу


