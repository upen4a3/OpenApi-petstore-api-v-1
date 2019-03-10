# OpenApi-petstore-api-v-1

```
This project is presents list of Rest API's ,which are useful to create a petstore and also to all kind of CRUD operations ,like Creating a petStore and retrive list of Pets from store and also deleting the existing pet.
```
## Getting Started

This project assumes developer has basic knowledge about developing api's using Spring boot and In memory databases.

### Prerequisites and technologies used
```
Java 1.8
Latest version Of STS or Eclipse
Maven central access
SpringMVC
Spring Boot
H2 In Memory database
RestController

##Testing tools
Postman
Mockito
Junit4
```
### Installing
```
Download the project and unzip it.

Import project as existing gradle project.

And Run the project as SpringBoot APP.

Once successfully run we should see "8080 (http) with context path '/petstore.swagger.io/api'" in eclipse console.
```

## Testing the API's
```
Open any api testing tool like postman or sopaUI

use the follwing url to retrive all pets from store.
```
Get :-http://localhost:8080/petstore.swagger.io/api/pets

### Break down into end to end tests


```
This project incudes good amount of junit test cases written using mockito.
To check the test cases go src/test/java

```



## Deployment

All the databases schema is included within the src/mai/resources folder.

## Built With


* [Gradle](https://gradle.org//) - Dependency Management



## Authors

* **Upendar Reddy** 

