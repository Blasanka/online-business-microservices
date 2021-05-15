## ABC Company's online business REST API as Micro services

### This repository contains four microservices.

1) ##### Auth Microservice (port: 8083)
   - This service handle authentication and authorization.
     Please note that this service implemented for demonstration purposes.
     So, there is proper authentication and authorization implementation
     with AOuth2 and JWT configuration.
   - Accessible url [http://localhost:8083/api/v1/auth/register](http://localhost:8083/api/v1/auth/register)
2) ##### User Microservice (port: 8080)
   - This service handles user management CRUD, search. 
   - Accessible url [http://localhost:8080/api/v1/users](http://localhost:8080/api/v1/users)
3) ##### Order Microservice (port: 8081)
    - This service handles order management CRUD, search and communicate
      Auth microservice to authorize before order management using dummy accessToken.
    - Accessible url [http://localhost:8081/api/v1/orders/](http://localhost:8081/api/v1/orders/)
4) ##### Payment Microservice (port: 8082)
    - This service handles dummy make payment and payment history, search and communicate
      Auth microservice and Order microservice to authorize before order management using dummy accessToken.
    - Accessible url [http://localhost:8082/api/v1/payments](http://localhost:8082/api/v1/payments)

When running each microservice, database have
to create but tables will create automatically.

- Find the database backups in root directory.
- There is a postman collection provided for testing
  all routes in four microservices.