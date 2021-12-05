#!/bin/sh -eu

db_address="mysql_db:3306"
db_user="root"
db_pass="root"

# uncomment hook
sed -e 's://\(@EventListener(ApplicationReadyEvent\.class)\):\1:g' -i src/main/java/pl/projectfiveg/services/UserService.java

mvn clean compile
mvn exec:java -Dexec.mainClass=pl.projectfiveg.Application \
	-Dapplication-url=http://0.0.0.0:8010 \
	-Dapplication-url-ws=ws://0.0.0.0:8010 \
	-Dspring.datasource.url="jdbc:mysql://${db_address}/projectfiveg?serverTimezone=UTC" \
	-Dspring.datasource.username="${db_user}" \
	-Dspring.datasource.password="${db_pass}" \
	-Dspring.jpa.hibernate.ddl-auto=create &
pid=$!

sleep 1m # should be enough, I guess
kill $pid

echo "if you didn't run this in in a temporary docker container you'll have to undo sed substitution and rebuild the project"
