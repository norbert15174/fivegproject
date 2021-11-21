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
- uncomment in userService (ctrl + n) -> (search userService) -> (uncomment the lines below in the constructor)
```
//        Set <User> users = Set.of(
//                new User("linux" , passwordEncoder.encode("linuxlinux") , Role.ROLE_LINUX , generateSalt()) ,
//                new User("ios" , passwordEncoder.encode("iosios") , Role.ROLE_IOS , generateSalt()) ,
//                new User("android" , passwordEncoder.encode("androidandroid") , Role.ROLE_ANDROID , generateSalt()));
//        userRepository.saveAll(users);
```
- build project (all dependencies should be downloaded automaticlly, if it does not, use terminal (mvn clean -DskipTests)
- comment the lines again
- use documentation for proper integration with your system (restapi-doc-notfinished.pdf)
