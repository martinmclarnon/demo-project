
FROM liquibase/liquibase:latest
COPY ./changelog/. /liquibase/changelog
CMD liquibase --sql-log-level=INFO --log-level=INFO update && tail -f /dev/null