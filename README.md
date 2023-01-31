[![CircleCI](https://dl.circleci.com/status-badge/img/gh/josealonso/ProductService/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/josealonso/ProductService/tree/main)

### ProductService

A sample Spring Boot 3.0.0 app using the starter modules Web-MVC and MongoDB. Tests are included.

### Testing

- There is one integration test.

- The unit tests for the different layers will be added.


### The Jackson library

- Serialize ----> Conversion from Java object to String.

- Deserialize --> Conversion from String to Java object.

#### The JsonProperty Annotation

It overwrites the Jackson naming strategy.

```
@JsonProperty("productId")
@Null
private UUID id;
```

#### The JsonFormat Annotation

```
@JsonFormat(shape = JsonFormat.Shape.String)
private BigDecimal price;
```

```
@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.String)
private OffsetDateTime createdDate;
```

### Spring Boot Actuator

It allows to monitor the application using HTTT endpoints and JMX.
