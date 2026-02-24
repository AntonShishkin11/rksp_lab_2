# RKSP Lab 2 — Data Service (rksp_lab_2_client)

Это **внутренний сервис (data-service)**. Он:
- поднимает REST API по контракту OpenAPI (`openapi-data.yaml`);
- сохраняет/читает студентов из PostgreSQL;
- отдает Swagger UI.

Порт по методичке: **8083**.

---

## Что нужно заранее

- **Java 17**
- **Docker + Docker Compose** (для PostgreSQL)
- (опционально) Maven, но в проекте есть `./mvnw`

---

## Быстрый старт

### 1) Клонирование

```bash
git clone https://github.com/AntonShishkin11/rksp_lab_2_client.git
cd rksp_lab_2_client
```

### 2) Поднять PostgreSQL

В проекте есть `compose.yaml` (Docker Compose).

```bash
docker compose up -d
```

Проверка:
```bash
docker ps
```

### 3) Собрать проект (генерация OpenAPI + компиляция)

```bash
./mvnw clean compile
```

### 4) Запустить data-service

```bash
./mvnw spring-boot:run
```

---

## Swagger

- Swagger UI: `http://localhost:8083/swagger-ui/index.html`
- OpenAPI JSON (если нужен): обычно `http://localhost:8083/v3/api-docs`

---

## Проверка работоспособности

### 1) Создать запись (POST /students)

Swagger → `POST /students` → **Try it out** → пример тела:

```json
{
  "fullName": "Иванов Иван Иванович",
  "passport": "1234 567890"
}
```

Ожидаемо: **200 OK** и ответ с `id`.

### 2) Получить по id (GET /students/{id})

Swagger → `GET /students/{id}` → подставь id из POST.

Ожидаемо: **200 OK** и те же данные.

### 3) Получить список (GET /students)

Swagger → `GET /students`

Ожидаемо: **200 OK** и массив студентов.

---

## Как понять, что БД реально используется

- После `POST /students` запись должна появиться в таблице `utmn.student`.
- Если используешь psql/pgAdmin: проверь запросом:

```sql
select * from utmn.student;
```

---

## Частые проблемы (потому что такова жизнь)

- **501 Not Implemented** при вызове эндпоинта  
  Значит интерфейс сгенерирован, а реализация в контроллере не сделана или не совпадает `operationId`/метод.
- **Не поднимается БД**  
  Проверь, что Docker запущен, и порт Postgres не занят.
- **Liquibase не создал схему/table**  
  Проверь `application.properties` и `db/changelog` (если есть), и лог старта приложения.

---

## Структура проекта (в общих чертах)

- `src/main/resources/openapi-data.yaml` — контракт API data-service (источник для генерации)
- `target/generated-sources/...` — сгенерированные интерфейсы/модели (не правим руками)
- `src/main/java/...` — контроллеры/сущности/репозитории

