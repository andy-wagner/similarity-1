# Similarity
Similarity between products

The returns of the API:
* 200 - Operation OK
* 400 - Some of the fields is empty
* 500 - There are some error, please contact the developer

#### Used Tecnologic
To increase component performance is used GraalVM (OpenJ9) with Java 11.

The API and DataBase access is used Spring (BOOT, WEB and JPA). Spring was chosen because of the simplicity of the development and might not be the faster framework, but it has enought performance for most situations.

Also Swagger was used to document the API and also give a easy access for testing the endpoints.

#### Lombok - Getters and Setters
In this project is used lombok to generated getters and setters, when compiles project by maven it will be auto-generated.

https://projectlombok.org/

Intellij or Eclipse

If you are going to use this project using Eclipse or Intellij please install Lombok plugin on your IDE.

* Intellij: https://projectlombok.org/setup/intellij
* Eclipse: https://projectlombok.org/setup/eclipse

#### ENDPOINTs
To retrive the vectors a product´s list use the URI /v1/vectors on method POST

    Payload:
    [
      {
        "id": 0,
        "name": "string",
        "tags": ["string"],
      }
    ]

To retrive the similarity from a product´s list use the URI /v1/similarity/{id} on method POST

    Payload:
    [
      {
        "id": integer,
        "name": "string",
        "tags": ["string"],
      },
      "vectors": [integer]
    ]



*Note: Since alterations will be made on payload, this methods cannot be a GET.

#### API DOCUMENTATION
To access Swagger Api Documentation just go to URI /swagger-ui.htm


#### TESTs COVERAGE
The testes coverage is calculated using JACOCO and maven will not compile the project if the coverage is under 70%.
