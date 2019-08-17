# Currency Exchange Micro Service - Mysql

Run com.in28minutes.microservices.currencyconversionservice.CurrencyConversionServiceApplicationMySql as a Java Application.

Application uses h2 database to run the tests.

## Changes from H2 Application

#### pom.xml

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>test</scope>
</dependency>

<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### src/main/resources/application.properties

```
#spring.h2.console.enabled=true
#spring.h2.console.settings.web-allow-others=true

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${RDS_HOSTNAME:localhost}:${RDS_PORT:3306}/${RDS_DB_NAME:exchange-db}
spring.datasource.username=${RDS_USERNAME:exchange-db-user}
spring.datasource.password=${RDS_PASSWORD:dummyexchange}
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
```

#### src/test/resources/application.properties

```
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```

## Dummy Data

Removed data.sql

```
long count = repository.count();

if(count ==0) {
	repository.save(new ExchangeValue(10001L,"USD","INR", BigDecimal.valueOf(65)));
	repository.save(new ExchangeValue(10002L,"EUR","INR", BigDecimal.valueOf(75)));
	repository.save(new ExchangeValue(10003L,"AUD","INR", BigDecimal.valueOf(25)));
}
```

## My SQL

### Launching MySQL using Docker

```
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=exchange-db-user --env MYSQL_PASSWORD=dummyexchange --env MYSQL_DATABASE=exchange-db --name mysql --publish 3306:3306 mysql:5.7
```


### My SQL Shell Client

- https://dev.mysql.com/downloads/shell/

- Install on mac using `brew install caskroom/cask/mysql-shell`.


```
Rangas-MacBook-Air:aws-projects in28min$ mysqlsh
MySQL Shell 8.0.15
Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
Other names may be trademarks of their respective owners.

Type '\help' or '\?' for help; '\quit' to exit.

MySQL  JS > \connect todos-user@localhost:3306
Creating a session to 'todos-user@localhost:3306'
Please provide the password for 'todos-user@localhost:3306': 
Save password for 'todos-user@localhost:3306'? [Y]es/[N]o/Ne[v]er (default No): v
Fetching schema names for autocompletion... Press ^C to stop.
Your MySQL connection id is 37
Server version: 5.7.26 MySQL Community Server (GPL)
No default schema selected; type \use <schema> to set one.

 MySQL  localhost:3306 ssl  JS > \sql
Switching to SQL mode... Commands end with ;

 MySQL  localhost:3306 ssl  SQL > use todos
Default schema set to `todos`.
Fetching table and column names from `todos` for auto-completion... Press ^C to stop.

 MySQL  localhost:3306 ssl  todos  SQL > select * from todo ;
+----+--------------+---------+----------------------------+-------------+
| id | description  | is_done | target_date                | user        |
+----+--------------+---------+----------------------------+-------------+
|  1 | Default Desc | 0       | 2019-06-26 18:30:00.000000 | in28minutes |
+----+--------------+---------+----------------------------+-------------+
1 row in set (0.0032 sec)

```

## Containerization

### Troubleshooting

- Problem - Caused by: com.spotify.docker.client.shaded.javax.ws.rs.ProcessingException: java.io.IOException: No such file or directory
- Solution - Check if docker is up and running!
- Problem - Error creating the Docker image on MacOS - java.io.IOException: Cannot run program “docker-credential-osxkeychain”: error=2, No such file or directory
- Solution - https://medium.com/@dakshika/error-creating-the-docker-image-on-macos-wso2-enterprise-integrator-tooling-dfb5b537b44e

### Creating Containers

- mvn package
- docker run --publish 8000:8000 --network MY_BRIDGE --name currency-exchange-microservice in28min/aws-currency-exchange-service-mysql:0.0.1-SNAPSHOT

Test API 
- http://localhost:8000/api/currency-exchange-microservice/currency-exchange/from/USD/to/INR

```
docker login
docker push @@@REPO_NAME@@@/aws-currency-exchange-service-mysql:0.0.1-SNAPSHOT
```

## Environment Variables

SSM URN - arn:aws:ssm:us-east-1:<account-id>:parameter/<name>

- /dev/currency-exchange-service/RDS_DB_NAME  - exchange_db
- /dev/currency-exchange-service/RDS_HOSTNAME	
- /dev/currency-exchange-service/RDS_PASSWORD	
- /dev/currency-exchange-service/RDS_PORT     - 3306
- /dev/currency-exchange-service/RDS_USERNAME - exchange_db_user



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
