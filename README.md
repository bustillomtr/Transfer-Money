# Transfer-Money

A simple Java Rest API that retrieves balance of an account and can perform transactions like **DEPOSIT, WITHDRAW and TRANSFER** of funds.

This is not a real world application. There is no security nor transaction management.

### How to run
```sh
mvn spring-boot:run
```
### Running test
```sh
mvn test
```

Apache Tomcat server is used and listening on a default port 8080.
An H2 in memory database initialized with some sample data.

### Sample Get Method
- http://localhost:8080/getbalance/1001

### Put Method
- http://localhost:8080/transaction

### To view API documentation
- http://localhost:8080/swagger-ui.html#/

