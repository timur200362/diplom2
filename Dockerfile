# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы сборки (например, Gradle или Maven)
COPY gradle gradle
COPY gradlew gradlew
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Собираем проект
RUN ./gradlew build -x test

# Создаем финальный образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированное приложение из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Открываем порт, на котором будет работать сервер
EXPOSE 8080
