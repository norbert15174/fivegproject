# Distributed Network Traffic Generator 
5G Networks course project - server

To run and test this server you need to have application.properties file.  
Contact Norbert Faron or Mikołaj Telec to receive it (via Messenger or MS Teams).


### Setup
- clone project from github
- add application.properties file to /src/main/resources
- changed database connection to your local mySql server
- uncomment in application.properties
```
#spring.jpa.hibernate.ddl-auto=create
```
- uncomment in userService (ctrl + n) -> (search userService) -> (uncomment the line with @EventListener(ApplicationReadyEventStart.class)
```
Method should include
//        Set <User> users = Set.of(
//                new User("linux" , passwordEncoder.encode("linuxlinux") , Role.ROLE_LINUX , generateSalt()) ,
//                new User("ios" , passwordEncoder.encode("iosios") , Role.ROLE_IOS , generateSalt()) ,
//                new User("android" , passwordEncoder.encode("androidandroid") , Role.ROLE_ANDROID , generateSalt()));
//        userRepository.saveAll(users);
```
- build project (all dependencies should be downloaded automaticlly, if it does not, use terminal (mvn clean -DskipTests)
- comment the lines again
- use documentation for proper integration with your system (restapi-doc-notfinished.pdf)


#### Docker setup

To build and start the service in a docker container simply run:

```sh
docker-compose build
docker-compose up -d
```

Run `docker-compose down` to stop and remove containers.
MySQL data will be preserved in a persistent volume.

In order to populate empty database do:

```sh
# assuming service and database containters are running
docker-compose stop server
docker-compose run --rm server ./populate.sh
# wait one minute
docker-compose start server
```
