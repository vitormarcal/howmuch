FROM node:22.0.0-alpine as frontend_builder

WORKDIR /app

COPY --link frontend/package.json ./frontend/package-lock.json ./
RUN npm install
COPY --link frontend .
RUN npm run generate
RUN npm prune

FROM amazoncorretto:21-alpine as backend_builder

WORKDIR /app

COPY --link gradle ./gradle
COPY --link build.gradle.kts gradlew gradlew.bat settings.gradle.kts ./
COPY --link src ./src

RUN ./gradlew clean build

FROM amazoncorretto:21-alpine

WORKDIR /app
COPY --from=frontend_builder /app/.output/public /app/public
COPY --from=backend_builder /app/build/libs/howmuch-0.0.1.jar howmuch.jar

VOLUME /config
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "howmuch.jar", "--spring.config.additional-location=file:/config/", "--spring.web.resources.static-locations=file:/app/public"]