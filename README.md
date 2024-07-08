# Books API Project

This repository contains a books api project written in Spring Boot.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) 17
- Maven 3.9+ (if using Maven for build)

## Build
If you are a Docker user, highly recommend you jump to [Containerized](#Containerized) section directly.

Follow these steps to build the project:
1. **Clone the repository:**
   ```sh
   git clone https://github.com/gumaku/book-management-service.git
   cd book-management-service
   ```
2. **Build the project using Maven:**
   ```sh
   mvn clean package
   ```
    This command will compile the project, run tests, and package the application into an executable JAR file.

## Run

To run the application locally:

1. **Navigate to the target directory:**
   ```sh 
   cd target
   ```
2. **Execute the JAR file:**
   ```sh 
   java -jar -Dspring.profiles.active=dev book-api-0.0.1-SNAPSHOT.jar
   ```
3. **Access the API:**
   Once the application has started, you can access the API using http://localhost:8080 (or the configured port).

## Containerized

You can also deploy this app in a container using the provided Dockerfile.

1. **Navigate to the project directory and execute the follow command to build the docker image:**
   ```sh 
   docker build -t="as/book-api" .
   ```
2. **Start the container:**
   ```sh 
   docker run --name my-book-api -p 8080:8080 -td as/book-api
   ```
3. **Health check:**
   ```sh 
   curl --location 'http://localhost:8080/actuator/health'
   ```

## API Documentation
The API documentation (Swagger/OpenAPI) is available at /swagger-ui.html when the application is running.

## API call using Postman 

postman collection is provided under project directory named AIA assignment book **AIA_assignment-book-api.postman_collection.json**

The following including some example screenshots of the API call using postman:

### Health-check
![image](https://github.com/gumaku/book-management-service/blob/main/images/img_health.png)
### Create a book
![image](https://github.com/gumaku/book-management-service/blob/main/images/img_create.png)
### Get books
![image](https://github.com/gumaku/book-management-service/blob/main/images/img_get1.png)
### Delete a book
![image](https://github.com/gumaku/book-management-service/blob/main/images/img_delete1.png)
