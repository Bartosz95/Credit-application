# CREDIT APPLICATION
## Necessary tools:
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

## Installation process:
### For installation run command below
[Download](https://github.com/Bartosz95/credit-application/archive/master.zip) and unzip or clone repository from github:
```shell script
git clone https://github.com/Bartosz95/credit-application.git
```
Change directory to credit-application/ :
```shell script
cd credit-application/
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
#
## Use 
You can communicate with this web service for example by [POSTMAN](https://www.getpostman.com/) program.
### Create Credit
For create new credit need to prepare and send message looks like below:

Method:
```html
POST
```
URL address:
```html
http://localhost:8090/CreateCredit
```
Headers parameter:
```html
Content-Type: application/json
```
Body message:
```json
{
    "customer": {
        "firstName": "Jan",
        "lastName": "Kowalski",
        "personalId": 1234567890
    },
    "product": {
        "name": "motgage credit",
        "value": 2000
    },
    "credit": {
        "name": "mortgage"
    }
}
```
### Get Credits
For get all credits go to [page](http://localhost:8090/GetCredits) or send message looks like:

Method:
```html
GET
```
URL address:
```html
http://localhost:8090/GetCredits
```
