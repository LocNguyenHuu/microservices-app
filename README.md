
# Microservices based application

This project is a base application which follows a microservices based architecture and is **DDD** oriented.

## Functionality
The main functionalities of the application are the following ones:
  - **Sign up**: the user can create a new account in the application.
  - **Login**: the user can authenticate in the application using his account credentials (email and password).
  - **Get profile data**: an authenticated user can check his profile data.
  - **Change profile data**: the user can change his profile data.
  - **Change password**: the user has the possibility of changing his password.
  - **Delete account**: the user can delete his account.
  - **Get users**: the administrator user can see the list of the application users. The default admin user has the following credentials: 
  ``email: admin@gmail.com, password: 1234``

All the public end-points are available in *public-end-points.postman_collection* Postman collection located in the project root.

## Requirements
Before start running the application, the following setup is needed:
- JDK 8
- Maven
- MongoDB database running on port **27017**
- The following ports available: 8061, 8088, 8091, 8097, 8095, 8096, 8098, 8099, 9100, 9200 (except 8088, the rest can be changed in config service)

## How to use
In order to run the application, you need to execute the following commands:
```bash
$ cd library/core-root
$ mvn clean install
```

```bash
$ cd library/core-security
$ mvn clean install
```

```bash
$ cd library/core-utils
$ mvn clean install
```

```bash
$ cd library/microservice-root
$ mvn clean install
```

After this, you need to build and deploy all the services in different consoles with the following command:

```bash
$ cd MICROSERVICE_PATH
$ mvn clean install
$ mvn spring-boot:run
```
You need to build and deploy all the microservices in the following order:

1. config
2. servicediscovery
3. users
4. passwordmanager
5. permissions
6. signup
7. authentication
8. userprofile
9. administration
10. apigateway

## Architecture
The web application uses the following technologies:
  -  **Spring Boot**: all the microservices are built using this framework.
  -  **Spring Cloud**: used for making microservice architecture possible.
  - **Spring WebFlux**: all the application is fully-asynchronous, as it uses Webflux reactive types: Mono and Flux.
  - **Orika**: library used for mapping the data through the application. 
  - **MongoDB**: the database chosen for the application is MongoDB, as it can be used in an asynchronous way.
  - **Mongobee**: tool used for populating the database with the default admin user.

The following diagram represents the connections between all the microservices:
<p align="center">
  <img src="https://raw.githubusercontent.com/sergiobanegas/microservices-app/master/diagrams/application-architecture.png">
</p>
The application consists of the following microservices: 

| Microservice| Description|
| ------ | ------ |
| Config| Used for achieving a centralized configuration. It contains all the properties of all the microservices. This is the only service that is not registered in the Service Discovery.|
| API Gateway| The only entry point of the application. It contains all the public end-points and defines different levels of security for each of them. The authorization is made via ***Authorization*** header, that must have the following pattern: ``Bearer JWT`` <br/> The public end-points are the following: <br/> - ***POST /auth/login***: handled by *authentication* service. Requires no authorization.<br/> - ***POST /sign-up***: handled by *sign-up* service. Requires no authorization. <br/> - ***GET/PATCH/DELETE /profile***: handled by *user-profile* service: User authorization required.<br/> - ***PUT /profile/password***: handled by *user-profile* service. User authorization required.<br/> - ***GET /admin/users***: handled by *administration* service. Requires admin privileges. |
| Service Discovery| Registers all the microservices together with their name and full address (host and port). |
| Sign-up|  This service is used for creating new users. This one is just an orchestrator that calls Users, Permissions and Password service to register the user data. The end-points of this service are non-secured from API Gateway (the user doesn't need to be authenticated to access). |
| Authentication|  Contains the login end-point, used for authenticate the user in the application. The service checks that the input credentials are correct and generates a Json Web Token (JWT), which is included in the authorization header. |
| Users|  Used for all user related functionalities (get all the users and get/create/modify/delete an user). It has direct access to the ***users*** database. |
| Password manager| It is used for all password related functionalities (create/modify/delete a password). It has direct access to the ***passwords*** database. |
| Permissions| It is used for all permission related functionalities (create a new user role and get the role of an user). It has direct access to the ***user_roles*** database. |
| User profile| This microservice handles all the operations related with the account data of an authenticated user (get/modify user data and delete account). |
| Administration| This service can be accessed only by an administrator user. The only functionality that it has is retrieving the list of users (calling Users service). |

Apart from this microservices, the application has the following libraries, used throughout the application:
  - **root**: has the parent pom of all the application.
  - **Microservice-root**: this library is the parent pom of all the microservices. It inherits from *root*.
  - **Core**: has multiple utilities and components for the microservices.
  - **Security**: contains all the security related functionality.

## Architecture of a microservice

The microservices architecture follows the ***DDD*** architecture (Domain-Driven design).
<p align="center">
  <img src="https://raw.githubusercontent.com/sergiobanegas/microservices-app/master/diagrams/architecture-of-a-microservice.png">
</p>


Each application, contains the following layers:

| Layer| Description|
| ------ | ------ |
| App| Contains only the main class.|
| Contract| Contains all the POJOs that are received/sent from the *client*. |
| Client | This layer has all the REST controllers. |
| Domain| Contains the POJOs and service interfaces that are used to exchange information between the *client* layer and the *infrastructure* layer. |
| Infrastructure| It has the main logic of the application. It contains:<br/>- **Services**: contain the business logic, orchestrating the calls to the different DAOs. <br/>- **DAOs**: used to access to DB or calling another microservices via HTTP. |
---

**Application made by Sergio Banegas Cortijo**
