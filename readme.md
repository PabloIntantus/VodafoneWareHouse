# CONFIGURATION
# Vodafone-warehouse | Vodafone-device-conf-service

> ## `MANDATORY`: Install Docker in your PC if you don't have it. It's necessary for Test Container.

> ## NOTE:
> I have assumed that the store has an application that will implement the 2 microservices since the document does not specify that the microservices interact with each other.

> Code quality: SonarLint (Plugin Intellij)

> # SWAGGER URL
> Warehouse: http://localhost:8080/swagger-ui/index.html#/
> Device-conf-service: http://localhost:8089/swagger-ui/index.html#/

# How to run the Application:

> You have 2 options to RUN, with docker or RUN locally using a mongoDB server
> #### `OPTION 1: DOCKER`:
> `MANDATORY`: Check application.yml if you use docker comment the line 10 (uri) and uncomment line 7 and 8 (Host and port) in the 2 projects
> To run the images with mongoDB you have to have installed docker and run the next commands

- 1: Pull the mongoDB image
```sh 
docker pull mongo
```
- 2: Run the mongoDB container
```sh 
docker run -d -p 27017:27017 --name mymongodb mongo:latest
```
- 3: Navigate where you have the project vodafone-warehouse. In my case:
```sh 
cd D:\Documentos Pablo\Trabajo y CV\GIT\vodafone-warehouse
```
- 4: Build the image vodafone-warehouse
```sh 
docker build -t vodafone-warehouse:1.0.0 .
```
- 5: Run the image vodafone-warehouse linked with mongoDB
```sh 
docker run -p 8080:8080 --name vodafone-warehouse --link mymongodb:mongo -d vodafone-warehouse:1.0.0
```
- 6: Navigate where you have the project vodafone-device-conf-service. In my case:
```sh 
cd D:\Documentos Pablo\Trabajo y CV\GIT\vodafone-warehouse
```
- 7: Build the image vodafone-device-conf-service
```sh 
docker build -t vodafone-device-conf-service:1.0.0 .
```
- 8: Run the image vodafone-device-conf-service linked with mongoDB
```sh 
docker run -p 8089:8089 --name vodafone-device-conf-service --link mymongodb:mongo -d vodafone-device-conf-service:1.0.0
```
- 9: To see the logs of the applications
```sh 
docker logs vodafone-warehouse
docker logs vodafone-device-conf-service
```
- 10: Now you have the 2 imagenes running in docker container
-- Warehouse: http://localhost:8080/swagger-ui/index.html#/
-- Device-conf-service: http://localhost:8089/swagger-ui/index.html#/


> #### `OPTION 2: LOCAL with MONGODB Server`:
> `MANDATORY`: Check application.yml if you use local with mongoDB server uncomment the line 10 (uri) and comment line 7 and 8 (Host and port) in the 2 projects

- 1: Run springboot with vodafone-warehouse
- 2: Run springboot with vodafone-device-conf-service
- 3: Now you have the 2 servers running
--Warehouse: http://localhost:8080/swagger-ui/index.html#/
--Device-conf-service: http://localhost:8089/swagger-ui/index.html#/
