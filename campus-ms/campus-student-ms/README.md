# Campus Student Microservice

### Student API

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

### MongoDB

Connect from the client
```
mongodb://campus:campus@localhost:37017/?authSource=admin
```

### Run Docker Services
```
cd ./docker
docker-compose up -d
```

### Links
- [Campus](https://github.com/ermalaliraj/campus)