FROM mysql:latest

COPY data.sql /etc/mysql/data.sql

ENV MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}

RUN cp /etc/mysql/data.sql /docker-entrypoint-initdb.d