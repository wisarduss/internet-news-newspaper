# Новостная интернет газета

## 1. Сборка проекта

1. Запустить приложение в контейнере docker-compose up -d

## 2. Получение и работа с JWT токеном

- Время жизни токена - 60 минут
- Для получения сделать POST запрос в Postman "http://localhost:8080/registration"

     {
       "name": "Maxim",
       "surname": "Borodulin",
       "email": "Borodulin@mail.ru",
       "password": "12345"
     }

После окончания жизни токена сделать запрос в Postman "http://localhost:8080/login"

      {
       "email": "Borodulin@mail.ru",
       "password": "12345"
      }

- Полученный JWT токен вставляем в header Authorization: Bearer JWT token

## 3. Диаграмма базы данных

![img.png](img.png)

## 4. Используемый стек технологий

1. Java 8
2. Spring Data JPA
3. Spring Security
4. Hibernate
5. JWT-api
6. PostgreSQL

## 5. Используемый стек технологий для тестирования

- Mockito

## 6. Используемые технологии для запуска

- Docker

## 7. Прмиперы эндпоинтов для Postman

- auth
    - POST "http://localhost:8080/registration" - регистрация нового пользователя
    - POST "http://localhost:8080/login" - аунтентификация пользователя

- users
    - GET "http://localhost:8080/users" - получение всех пользователей
    - GET "http://localhost:8080/users/{id}" - получение конкретного пользователя

- posts
    - POST   "http://localhost:8080/posts" - добавление нового поста
    - GET    "http://localhost:8080/posts/{id}" - поиск конкретного поста
    - PATCH  "http://localhost:8080/posts/{id}/{userId}" - обновление конкретного поста
    - GET    "http://localhost:8080/posts" - получение последних постов за 24 часа
    - DELETE "http://localhost:8080/posts/{id}/{userId}" - удаление поста

- likes
    - POST   "http://localhost:8080/likes" - добавление лайка к посту
    - DELETE "http://localhost:8080/likes/{likeId}/{userId}" - удаление лайка у поста

- comments
    - POST   "http://localhost:8080/comments{postId}/{userId}" - добавление комментария к посту
    - DELETE "http://localhost:8080/comments/{commentId}/{userId}" - удаление комментария
    
