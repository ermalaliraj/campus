# Campus

## Campus Ecosystem

- BackEnd
    - docker
        - MongoDB
    - Student Microservice
    - Course Microservice (todo)
- Front-End
    - React (todo)
    - Angular (todo)
    - JSF / Primefaces (todo)
    - End2End Automation Test Cucumber

### MongoDB

Run `MongoDB` inside a docker container.
```
cd ./docker
docker-compose up -d
```

Connect from the client
```
mongodb://campus:campus@localhost:37017/?authSource=admin
```

### Student Microservice

Run `CampusStudentApplication`, the service will be listen on the port `8085`
```
GET http://localhost:8085/students
GET http://localhost:8085/students?name=Er&surname=Al
GET http://localhost:8085/students/61e06f182f28bd1f82b5a0190

DELETE http://localhost:8085/students
DELETE http://localhost:8085/students/61e06f182f28bd1f82b5a0190

POST http://localhost:8085/students
{
    "name": "Ermal",
    "surname": "Aliraj",
    "registrationCode": "001"
}
```
