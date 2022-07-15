## MJC school

### Module #3 - REST API Advanced

This is a module that includes project migration from module #2 to Spring Boot.  
[Module #2](https://github.com/lainng/gift-sertificate) - REST API Basics.  
[Module #4](https://github.com/lainng/gift-certificate-security) - Authentication & Spring Security.

### *Task*

#### Part 1
Migrate your existing Spring application from a previous module to a Spring Boot application.

#### Part 2
Business requirements:  
The system should be extended to expose the following REST APIs:
- Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
- Add new entity User.
  - implement only get operations for user entity.
- Make an order on gift certificate for a user (user should have an ability to buy a certificate).
- Get information about user’s orders.
- Get information about user’s order: cost and timestamp of a purchase.
- The order cost should not be changed if the price of the gift certificate is changed.
- Get the most widely used tag of a user with the highest cost of all orders.
  - Create separate endpoint for this query.
  - Demonstrate SQL execution plan for this query (explain).
- Search for gift certificates by several tags (“and” condition).
- Pagination should be implemented for all GET endpoints. Please, create a flexible and non-erroneous solution. Handle all exceptional cases.
- Support HATEOAS on REST endpoints.

Application requirements:
- Hibernate should be used as a JPA implementation for data access.
- Spring Transaction should be used in all necessary areas of the application.
- Audit data should be populated using JPA features (an example can be found in materials).

Application restrictions:
- Hibernate specific features.
- Spring Data
