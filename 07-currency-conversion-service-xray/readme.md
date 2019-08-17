# Currency Conversion Micro Service

Run com.in28minutes.microservices.currencyconversionservice.CurrencyConversionServiceApplication as a Java Application.

## Changes for X Ray

- pom.xml - aws-xray-recorder-sdk-spring & aws-xray-recorder-sdk-apache-http
- AwsXrayConfig.java
- XRayInspector.java
- CurrencyConversionServiceApplication.java

```
	@Bean
	public RestTemplate restTemplate() {
		// return new RestTemplate();
		return new RestTemplate(clientHttpRequestFactory());

	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
				HttpClientBuilder.create().useSystemProperties().build());
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(2000);
		factory.setConnectionRequestTimeout(2000);
		return factory;
	}

```


- CurrencyConversionController.java

```
@RestController
@XRayEnabled
public class CurrencyConversionController {
```

## XRay Daemon

- amazon/aws-xray-daemon:1


## Containerization

### Troubleshooting

- Problem - Caused by: com.spotify.docker.client.shaded.javax.ws.rs.ProcessingException: java.io.IOException: No such file or directory
- Solution - Check if docker is up and running!
- Problem - Error creating the Docker image on MacOS - java.io.IOException: Cannot run program “docker-credential-osxkeychain”: error=2, No such file or directory
- Solution - https://medium.com/@dakshika/error-creating-the-docker-image-on-macos-wso2-enterprise-integrator-tooling-dfb5b537b44e

### Creating Containers

- mvn package
- docker run --publish 8100:8100 --network MY_BRIDGE --env CURRENCY_EXCHANGE_URI=http://currency-exchange-microservice:8000 in28min/aws-currency-conversion-service-xray:0.0.1-SNAPSHOT



Test API 
- http://localhost:8100/api/currency-conversion-microservice/currency-converter/from/USD/to/INR/quantity/1000
- http://localhost:8100/api/currency-conversion-microservice/currency-converter-feign/from/USD/to/INR/quantity/10000

```
docker login
docker push @@REPO@@/aws-currency-conversion-service-xray:0.0.1-SNAPSHOT
```


## Resources

- http://localhost:8100/api/currency-conversion-microservice/currency-converter-feign/from/USD/to/INR/quantity/10000

```json
{
  "id": 10001,
  "from": "USD",
  "to": "INR",
  "conversionMultiple": 65.00,
  "quantity": 1000,
  "totalCalculatedAmount": 65000.00,
  "environmentInfo": "NA"
}
```