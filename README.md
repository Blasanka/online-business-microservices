## ABC Company's online business REST API as Micro services

### This repository contains four microservices.

<strong> Please note:</strong>  In order to work below main four microservices, have to create databases by following names: <strong> abc_users, abc_payments, abc_orders, abc_tokens.</strong>

1) ##### Auth Microservice (port: 8083)
   - This service handle authentication and authorization.
     Please note that this service implemented for demonstration purposes.
     So, there is proper authentication and authorization implementation
     with AOuth2 and JWT configuration.
   - Accessible url [http://localhost:8083/api/v1/auth/register](http://localhost:8083/api/v1/auth/register)
   - Accessible Swagger doc url [http://localhost:8083/api/v1/swagger-ui.html](http://localhost:8083/api/v1/swagger-ui.html)
2) ##### User Microservice (port: 8080)
   - This service handles user management CRUD, search. 
   - Accessible url [http://localhost:8080/api/v1/users](http://localhost:8080/api/v1/users)
   - Accessible Swagger doc url [http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)
3) ##### Order Microservice (port: 8081)
    - This service handles order management CRUD, search and communicate
      Auth microservice to authorize before order management using dummy accessToken.
    - Accessible url [http://localhost:8081/api/v1/orders/](http://localhost:8081/api/v1/orders/)
    - Accessible Swagger doc url [http://localhost:8081/api/v1/swagger-ui.html](http://localhost:8081/api/v1/swagger-ui.html)
4) ##### Payment Microservice (port: 8082)
    - This service handles dummy make payment and payment history, search and communicate
      Auth microservice and Order microservice to authorize before order management using dummy accessToken.
    - Accessible url [http://localhost:8082/api/v1/payments](http://localhost:8082/api/v1/payments)
    - Accessible Swagger doc url [http://localhost:8082/api/v1/swagger-ui.html](http://localhost:8082/api/v1/swagger-ui.html)
    
<hr>
<em> Added for the completeness but not properly integrated with other microservices </em>

5) ##### Category Microservice (port: 8082)
    - For the sake of completeness, this microservices added but not communicate with other microservices
    - Accessible url [http://localhost:8082/api/v1/payments](http://localhost:8090/api/v1/categories)
   - Accessible Swagger doc url [http://localhost:8090/api/v1/payments](http://localhost:8092/api/v1/swagger-ui.html)
6) ##### Sub category Microservice (port: 8082)
    - For the sake of completeness, this microservices added but not communicate with other microservices except categories microservice
    - Accessible url [http://localhost:8082/api/v1/payments](http://localhost:8091/api/v1/sub-categories)
   - Accessible Swagger doc url [http://localhost:8091/api/v1/payments](http://localhost:8091/api/v1/swagger-ui.html)

When running each microservice, database have
to create but tables will create automatically.

- Find the table backups in root directory.
- There is a postman collection provided for testing
  all routes of four microservices.