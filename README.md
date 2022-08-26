# CREDIT 
## Abstract
Application gives you a API which can be use to menage loans of bank customers. 
It consists of three separate modules which are comunication throught http interface.
There are three services:
- credit - you can deal with this API,
- cunstomer where are collected information about customers and it handles requests form credit module,
- product where are collected information about credits and it also handles requests form credit module,
Communication with database was done by JDCB module

## Technologies
- Java
- Spring
- MySQL
- Maven
- Docker 

## Requirements
- Maven
- Docker

## Installation
First you need to download allication. 
[Download](https://github.com/Bartosz95/credit-api/archive/master.zip) and unzip or clone repository from github:
```shell script
git clone https://github.com/Bartosz95/credit-api.git
```
Next to change directory to credit-api/ :
```shell script
cd credit-api/
```
Build jar file in three module:
```shell script
mvn clean package
```
Build docker images and run containers: 
```shell script
mvn clean package docker:build docker:run
```
If it's throw some error, try to clean
```shell script
docker stop mysql-1 product-1 customer-1 credit-1 && docker rm mysql-1 product-1 customer-1 credit-1
```
## Tutorial
You can communicate with this web service example by sending requests to http://localhost:8090/.
For create new credit need to send POST request looks like below:
```
curl -X POST http://localhost:8090/CreateCredit
   -H 'Content-Type: application/json'
   -d '{ \
    "customer": { \
        "firstName": "Jan", \
        "lastName": "Kowalski", \
        "personalId": 1234567890 \
    }, \
    "product": { \
        "name": "motgage credit", \
        "value": 2000 \
    }, \
    "credit": { \
        "name": "mortgage" \
    } \
}'
```
You can get all credits by
```
curl -X POST http://localhost:8090/GetCredits
```
You can get single client's credits by
```
curl http://localhost:8090/GetCredits/:personalId
```