# Hello World Rest API

- Main class com.in28minutes.rest.webservices.restfulwebservices.RestfulWebServicesApplication 
- You cannot run this app on local as it is configured to run on port 80 - https://serverfault.com/questions/112795/how-to-run-a-server-on-port-80-as-a-normal-user-on-linux. You can run it as a docker container as shown below


### Creating Containers

- mvn clean package
- docker run --publish 8200:80 in28min/aws-hello-world-rest-api:0.0.1-SNAPSHOT

```
docker login
docker push @@REPO@@/aws-hello-world-rest-api:0.0.1-SNAPSHOT
```

## Test URLs

- http://localhost:8200/hello-world

```txt
Hello World
```

- http://localhost:8200/hello-world-bean

```json
{"message":"Hello World - Changed"}
```

- http://localhost:8200/hello-world/path-variable/in28minutes

```json
{"message":"Hello World, in28minutes"}
```
