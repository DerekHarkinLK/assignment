Commands $ run from git bash on windows:

Fix Formatting issues:
$ ./mvnw spring-javaformat:apply

Build WAR file:
$ ./mvnw clean install

Test WebApp:
copy \AtmMachineApp\target\AtmMachineApp-1.0-SNAPSHOT.war to \Appserver\webapps\
start server
URL - http://localhost:8080/AtmMachineApp-1.0-SNAPSHOT/LOGIN


Run Rest API:
$ ./mvnw spring-boot:run

	Browser Address
	http://localhost:8080/balance?accountNo=987654321&pin=4321
	http://localhost:8080/withdraw?accountNo=987654321&pin=4321&cash=95
	OR
	curl --request GET \
	--url "http://localhost:8080/balance?accountNo=987654321&pin=4321"
	curl --request GET \
	--url "http://localhost:8080/withdraw?accountNo=987654321&pin=4321&cash=95"


Build Docker Image:
$ docker-compose -f docker-compose.dev.yml up --build

	Browser Address
	http://localhost:8080/balance?accountNo=987654321&pin=4321
	http://localhost:8080/withdraw?accountNo=987654321&pin=4321&cash=95
	OR
	curl --request GET \
	--url "http://localhost:8080/balance?accountNo=987654321&pin=4321"
	curl --request GET \
	--url "http://localhost:8080/withdraw?accountNo=987654321&pin=4321&cash=95"
