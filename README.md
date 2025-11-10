### Запуск проекта

Создать `.env` по шаблону `.env.template` (если хотите поменять значения по умолчанию)

Выполнить команду:

```bash
docker compose up -d --build
```

Сервер будет доступен по адресу `http://localhost:8080/`

### Запуск тестов

Для запуска тестов выполните команду:

```bash
./mvnw test
```

Или на Windows:

```bash
mvnw.cmd test
```

Для запуска с подробным выводом:

```bash
./mvnw test -Dspring.profiles.active=test
```

### Результаты тестов

![tests.png](tests.png)