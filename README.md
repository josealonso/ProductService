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

The **/info** endpoint is not enabled by default.

The **/health** endpoint

The **/beans** endpoint is enabled by default.
It shows all the beans registered in the app, including those auto configured by Spring Boot.

The **/conditions** endpoint shows the auto configuration report, categorised into positiveMatches and negativeMatches.

The **/mappings** endpoint shows all the **@RequestMapping** paths declared in the application.

The **/configprops** endpoint shows all the configuration properties defined by **@ConfigurationProperties** bean, 
including those defined in the *application.properties* file.

The **/metrics** endpoint shows various metrics about the app such as how much memory is used, the size of the heap 
or the number of threads used.

The **/env** endpoint shows all the properties from the Spring's **ConfigurableEnvironment** interface, such as 
a list of active profiles, application properties or system environment variables.

The **/threaddumb** endpoint shows the app's thread dumb with running threads details and JVM stack trace.

The **/loggers** endpoint allows to view and configure the log levels of the app at runtime.

The **/shutdown** endpoint can be used to gracefully shut down the application.
Enable this endpoint:
```
management.endpoint.shutdown.enabled=true
```
and send a POST request with an empty body to the following endpoint
```
http://localhost:8080/actuator/shutdown
```





















