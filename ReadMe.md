# Sellics Assignment
This application is developed for Sellics assignment
	
## Assignment

* Create a microservice that query amazon suggestion service with the given keyword

* Estimate service

```   
estimate?keyword={keyword}
```


## Solution
* As SLA time is 10 seconds, many calls are made to suggestion service with the substring of given keyword
* A score is calculated depeding on the order of keyword in the returned suggestion list after every call
* Weighted average is used to calcualte final score




## Technologies

* Spring Boot - Framework
* Maven - Dependency Management

## Install and Run

First run command below in terminal

```
mvn clean install
```

Then run jar file with command below in terminal

```
java -jar target/sellicsTask.jar
```

## Test
Test can be run separately with commands below

```
mvn test
mvn verify
```

 