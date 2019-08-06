# API Developer Assignment

## 1. How to run
```
$ git clone https://github.com/mesutcang/api-dev-assignment.git
$ mvn spring-boot:run

```

## API Documentation
Swagger documentation can be accessible via 
```
http://localhost:8080/swagger-ui.html
```
## Endpoints

There are 2 endpoints:

### Categorised Transaction List

Get the transaction list for given category
```

HTTP GET /v1/{categoryId}
```

categoryId as b6as2

### Categorised Transaction List

Update the category info for given transaction
```

HTTP PUT /v1/updatecategory
```

Expecting data as
```

{
  "transactionId": "string",
  "categoryId": "string"
}
```
