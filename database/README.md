# Database

## Build liquibase image for mysql database
```
docker build . -t liquibase/liquibase-mysql
```

## Testing env
```
docker run --rm --network 5g-project-database_default liquibase/liquibase-mysql --url="jdbc:mysql://mysql_db:3306/test_db" --changeLogFile=dbchangelog.xml --username=root --password=root update
```

## Apply config on remote database
```
docker run --rm liquibase/liquibase-mysql --url="jdbc:mysql://<DB_IP>:3306/<DB_NAME>" --changeLogFile=dbchangelog.xml --username=<DB_USER> --password=<DB_PASSWORD> update
```