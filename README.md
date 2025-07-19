# Домашнее задание по Spring T1

## Описание

Проект состоит из двух основных модулей:

- `synthetic-human-core-starter` — библиотека, которая публикуется в локальный Maven-репозиторий.
- `restAndroidApp` — основное Android-приложение, использующее опубликованную библиотеку.

## Запуск проекта

Для запуска проекта необходимо выполнить следующие шаги:

Перейдите в каталог `synthetic-human-core-starter` и выполните:

```bash
./gradlew clean build
./gradlew publishToMavenLocal


```  
Затем перейдите в каталог `restAndroidApp` и выполните:
```bash
./gradlew clean build
docker-compose up
./gradlew bootRun
