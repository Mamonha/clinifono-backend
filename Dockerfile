FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root_password
ENV MYSQL_DATABASE=app_database
ENV MYSQL_USER=app_user
ENV MYSQL_PASSWORD=app_password

EXPOSE 3306

CMD ["mysqld"]
