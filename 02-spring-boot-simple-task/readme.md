# Simple Spring Task

Run `com.in28minutes.spring.simple.task.SpringSimpleTaskApplication` as a Java Application.

Runs `com.in28minutes.spring.simple.task.TaskImpl.performTask()` at launch

### Creating Containers

- mvn clean package
- docker run in28min/aws-simple-spring-task:0.0.1-SNAPSHOT

```
docker login
docker push @@REPO@@/aws-simple-spring-task:0.0.1-SNAPSHOT
```