FROM openjdk:17-alpine

ARG JAR_FILE

RUN mkdir -p /apps
COPY ./build/libs/${JAR_FILE} /apps/app.jar
COPY ./entrypoint.sh /apps/entrypoint.sh
RUN chmod +x /apps/entrypoint.sh

CMD ["/apps/entrypoint.sh"]