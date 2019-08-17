# Currency Exchange Micro Service - H2 with XRay

Run com.in28minutes.microservices.currencyconversionservice.CurrencyConversionServiceApplicationH2 as a Java Application.


## Changes from H2 Application

- pom.xml - aws-xray-recorder-sdk-spring
- AwsXrayConfig.java
- XRayInspector.java
- CurrencyExchangeController.java

```
@RestController
@XRayEnabled
public class CurrencyExchangeController {
```

## XRay Daemon

- amazon/aws-xray-daemon:1


## Containerization

### Troubleshooting

- Problem - Caused by: com.spotify.docker.client.shaded.javax.ws.rs.ProcessingException: java.io.IOException: No such file or directory
- Solution - Check if docker is up and running!
- Problem - Error creating the Docker image on MacOS - java.io.IOException: Cannot run program “docker-credential-osxkeychain”: error=2, No such file or directory
- Solution - https://medium.com/@dakshika/error-creating-the-docker-image-on-macos-wso2-enterprise-integrator-tooling-dfb5b537b44e

### Running Containers

- mvn package
- docker run --publish 8000:8000 --network MY_BRIDGE --name currency-exchange-microservice in28min/aws-currency-exchange-service-h2-xray:0.0.1-SNAPSHOT

Test API 
- http://localhost:8000/api/currency-exchange-microservice/currency-exchange/from/USD/to/INR

```
docker login
docker push @@@REPO_NAME@@@/aws-currency-exchange-service-h2-xray:0.0.1-SNAPSHOT
```

## Resources

- http://localhost:8000/api/currency-exchange-microservice/currency-exchange/from/USD/to/INR

```json
{
  "id": 10001,
  "from": "USD",
  "to": "INR",
  "conversionMultiple": 65.00,
  "environmentInfo": "NA"
}
```

## H2 Console

- http://localhost:8000/h2-console
- Use `jdbc:h2:mem:testdb` as JDBC URL


## Tables Created
```
create table exchange_value 
(
	id bigint not null, 
	conversion_multiple decimal(19,2), 
	currency_from varchar(255), 
	currency_to varchar(255), 
	primary key (id)
)
```
