#Description
Bank Service is a simple banking core system, where you have 
users and cards where user can do simple transactions.

#Requirements
**Java 8**

**Maven 3.6.3**

**Docker**

**MySQL 8.0**

#Dependencies
Bank Service uses MySQL for storing users and cards.

#Configuration


Here is configuration for connect Bank Service

**spring.datasource.url=jdbc:MySQL://bank-service-db:3306/database**

**spring.datasource.username=root**

**spring.datasource.password=root**

#Build and deploy
* run docker command **docker-compose up** to set up environment
* OR
* Run BankServiceApplication and MySQL db

#User Manual
* Visit Swagger configuration to see endpoints.
* http://localhost:8080/swagger-ui/index.html
* First add user then associated cards
* Test it from ATM service.

