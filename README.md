# Deploy Spring Boot and Docker Microservices to AWS using ECS and AWS Fargate

[![Image](https://www.springboottutorial.com/images/Course-Deploy-Java-Spring-Boot-Microservices-To-ECS.png "Deploying Spring Boot Microservices to AWS using ECS and AWS Fargate")](https://www.udemy.com/course/deploy-spring-microservices-to-aws-with-ecs-and-aws-fargate/)


## Learn Amazon Web Services - AWS - deploying Spring Boot and Docker Microservices to AWS Fargate. Implement Service Discovery, Load Balancing, Auto Discovery, Centralized Configuration and Distributed Tracing in AWS.

Spring Boot is the No 1 Java Framework to develop REST API and Microservices. AWS (Amazon Web Services) is the No 1 Cloud Service Provider today.

How about learning AWS by deploying Spring Boot Docker Containers to Amazon Web Services using Elastic Container Service - ECS and AWS Fargate?

## Getting Started

- [Video - Docker in 5 Steps](https://youtu.be/Rt5G5Gj7RP0)
- [Video - Spring in 10 Steps](https://www.youtube.com/watch?v=edgZo2g-LTM)
- [Video - Spring Boot in 10 Steps](https://www.youtube.com/watch?v=pcdpk3Yd1EA)
- [Video - JPA/Hibernate in 10 Steps](https://www.youtube.com/watch?v=MaI0_XdpdP8)
- [AWS Code Pipeline Github Repo](https://github.com/in28minutes/hello-world-rest-api-aws-ecs-codepipeline)

## Container Images

|     Application                 |    Container                                  |
| ------------------------------- | --------------------------------------------- |
| Hello World | in28min/aws-hello-world-rest-api:1.0.0-RELEASE |
| Simple Task | in28min/aws-simple-spring-task:1.0.0-RELEASE |
| CurrencyExchangeMicroservice-H2 | in28min/aws-currency-exchange-service-h2:0.0.1-SNAPSHOT |
| CurrencyExchangeMicroservice-H2 - V2 | in28min/aws-currency-exchange-service-h2:1.0.1-RELEASE|
| CurrencyExchangeMicroSevice-MySQL| in28min/aws-currency-exchange-service-mysql:0.0.1-SNAPSHOT|
| CurrencyConversionMicroservice | in28min/aws-currency-conversion-service:0.0.1-SNAPSHOT |
| Currency Exchange - X Ray | in28min/aws-currency-exchange-service-h2-xray:0.0.1-SNAPSHOT|
| Currency Conversion - X Ray | in28min/aws-currency-conversion-service-xray:0.0.1-SNAPSHOT|

|     Utility       |     Container Image        |
| ------------- | ------------------------- |
| aws-xray-daemon| amazon/aws-xray-daemon:1|

## Microservice URLs and Details

### Currency Exchange Service

- PORT - 8000
- URL - `http://localhost:8000/api/currency-exchange-microservice/currency-exchange/from/EUR/to/INR`
- HEALTH URL - `http://localhost:8000/api/currency-exchange-microservice/manage/health`
- Enviroment Variables
  - SSM URN - `arn:aws:ssm:us-east-1:<account-id>:parameter/<name>`
  - /dev/currency-exchange-service/RDS_DB_NAME  - exchange_db
  - /dev/currency-exchange-service/RDS_HOSTNAME 
  - /dev/currency-exchange-service/RDS_PASSWORD 
  - /dev/currency-exchange-service/RDS_PORT     - 3306
  - /dev/currency-exchange-service/RDS_USERNAME - exchange_db_user

### Currency Conversion Service

- PORT - 8100
- URL - `http://localhost:8100/api/currency-conversion-microservice/currency-converter/from/USD/to/INR/quantity/10`
- HEALTH URL - `http://localhost:8100/api/currency-conversion-microservice/manage/health`
- Enviroment Variables
  - SSM URN - `arn:aws:ssm:us-east-1:<account-id>:parameter/<name>`
  - /dev/currency-conversion-service/CURRENCY_EXCHANGE_URI

## Enviroment Variables

SSM URN - `arn:aws:ssm:us-east-1:<account-id>:parameter/<name>`

- /dev/currency-conversion-service/CURRENCY_EXCHANGE_URI
- /dev/currency-exchange-service/RDS_DB_NAME  - exchange_db
- /dev/currency-exchange-service/RDS_HOSTNAME 
- /dev/currency-exchange-service/RDS_PASSWORD 
- /dev/currency-exchange-service/RDS_PORT     - 3306
- /dev/currency-exchange-service/RDS_USERNAME - exchange_db_user

## Setting up App Mesh

#### Virtual nodes 
- currency-exchange-service-vn - currency-exchange-service.in28minutes-dev.com
- currency-conversion-service-vn - currency-conversion-service.in28minutes-dev.com

#### Virtual services 
- currency-exchange-service.in28minutes-dev.com -> currency-exchange-service-vn
- currency-conversion-service.in28minutes-dev.com -> currency-conversion-service-vn

#### Backend Registration
- currency-conversion-service-vn -> currency-exchange-service.in28minutes-dev.com

#### Task Definition Updates
- aws-currency-conversion-service
- aws-currency-exchange-service-h2
- ```ENVOY_LOG_LEVEL-trace, ENABLE_ENVOY_XRAY_TRACING-1```

#### Service Updates
- aws-currency-conversion-service-appmesh
- aws-currency-exchange-service-appmesh

## Deploying Version 2 of Currency Exchange Service to ECS and App Mesh

#### App Mesh - New Virtual Node
currency-exchange-service-v2-vn - currency-exchange-service-v2.in28minutes-dev.com

#### ECS Fargate - Update Task Definition
aws-currency-exchange-service-h2
 - in28min/aws-currency-exchange-service-h2:1.0.1-RELEASE
 - Use New Virtual Node
 
#### ECS Fargate - Create New Service 
aws-currency-exchange-service-v2-appmesh
- Service Discovery - currency-exchange-service-v2

#### App Mesh - Create Virtual Router
currency-exchange-service-vr distributing traffic to
- currency-exchange-service-vn
- currency-exchange-service-v2-vn

#### App Mesh - Update Service to Use Virtual Router
currency-exchange-service.in28minutes-dev.com -> currency-exchange-service-vr


#### jq

```
sudo yum install jq
```

#### AWS CLI Hosted Zones

```
aws --version
aws configure
aws servicediscovery list-services
aws servicediscovery delete-service --id={id}
aws servicediscovery list-services

aws servicediscovery list-namespaces
aws servicediscovery delete-namespace --id={id}
aws servicediscovery list-namespaces

aws servicediscovery delete-service --id=srv-7q3fkztnbo6aa5kc
aws servicediscovery delete-service --id=srv-mdybugm4bh5u4ugx
aws servicediscovery delete-service --id=srv-7upzjx3mhfleyfoz
aws servicediscovery delete-namespace --id=ns-ctvtysasurklojm3
```

## Installation Guides

#### Required Tools

- Java 8+
- Eclipse - Oxygen+ - (Embedded Maven From Eclipse)
- Git
- Docker

#### Installing Guides

- [Playlist - Installing Java, Eclipse & Embedded Maven](https://www.youtube.com/playlist?list=PLBBog2r6uMCSmMVTW_QmDLyASBvovyAO3)

#### Troubleshooting Installations
- Eclipse and Embedded Maven
  - Troubleshooting Guide : https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#tip--troubleshooting-embedded-maven-in-eclipse
  - PDF : https://github.com/in28minutes/SpringIn28Minutes/blob/master/InstallationGuide-JavaEclipseAndMaven_v2.pdf
  - GIT Repository For Installation : https://github.com/in28minutes/getting-started-in-5-steps

## Course Overview

This course would be a perfect first step as an introduction to Amazon Web Services - AWS and the Cloud.

In this course, we deploy a variety of Java Spring Boot Microservices to Amazon Web Services using AWS Fargate and ECS - Elastic Container Service. 

You will learn the basics of implementing Container Orchestration with ECS (Elastic Container Service) - Cluster, Task Definitions, Tasks, Containers and Services. You will learn about the two launch types of ECS - EC2 and AWS Fargate. In this course, we would focus extensively on AWS Fargate to simplify your Container Orchestration. You will learn to deploy multiple containers in the same ECS task.

You will learn to Build Container Images for your Java Spring Boot Microservice Projects.

You will implement the following features for your Microservices
- Centralized Configuration Management with AWS Parameter Store
- Distributed Tracing with AWS X Ray
- Auto Scaling and Load Balancing with ECS, Elastic Load Balancers and Target Groups
- Service Mesh using AWS App Mesh. You will learn the basics of AWS App Mesh - Mesh, Virtual Nodes and Virtual Services. You will learn to perform Canary Deployments using AWS AppMesh.
- Service Discovery with Route 53 Hosted Zones and DNS.
- Continuous Integration and Continuous Deployment with AWS Code Pipeline

You will learn to debug problems with deploying containers using Service events and AWS CloudWatch logs.

You will learn about automating deployments and creating a continuous delivery pipeline with AWS Code Pipeline. You will learn how to Auto Scale applications based on load as well as deploy multiple instances behind a load balancer using AWS ECS Fargate.

You will be using a number of AWS Services - ECS - Elastic Container Services, AWS Fargate, EC2 - Elastic Compute Cloud, S3, AWS CodePipeLine, AWS CodeBuild, IAM, CloudWatch, ELB, Target Groups, X Ray, AWS Parameter Store, AWS App Mesh and Route 53. 

You will be using deploying a variety of projects to Amazon Web Services (AWS). These projects are created with  Spring Boot (REST API Framework), Spring (Dependency Management), Maven (dependencies management), Eclipse (Java IDE) and Tomcat Embedded Web Server. We will help you set up each one of these.

## What you'll learn
- You will Learn the Fundamentals of Amazon Web Services from Zero, no previous experience required
- You will learn to deploy Spring Boot REST API and Microservices to AWS with AWS Fargate and ECS
- You will learn the basics of implementing Container Orchestration with ECS (Elastic Container Service) - Cluster, Task Definitions, Tasks, Containers and Services
- You will learn to Build Container Images for your Java Spring Boot Microservice Projects
- You will learn about the two launch types of ECS - EC2 and AWS Fargate
- You will learn to debug problems with deploying containers using Service events and AWS CloudWatch logs
- You will be using a number of AWS Services - ECS - Elastic Container Services, AWS Fargate, EC2 - Elastic Compute Cloud, S3, AWS CodePipeLine, AWS CodeBuild, IAM, CloudWatch, ELB, Target Groups, X Ray, AWS Parameter Store, AWS App Mesh and Route 53
- You will learn to create a continuous delivery pipeline with AWS Code Pipeline
- You will learn how to Auto Scale applications based on load as well as deploy multiple instances behind a load balancer using AWS Fargate.
- You will learn to implement Centralized Configuration Management for your Java Spring Boot Microservices with AWS Parameter Store
- You will learn to implement Distributed Tracing for Java Spring Boot Microservices with AWS X Ray
- You will learn the basics of AWS App Mesh - Mesh, Virtual Nodes and Virtual Services. You will learn to perform Canary Deployments for Java Spring Boot Microservices using AWS AppMesh.
- You will Join 300,000 Learners having AMAZING LEARNING Experiences with in28Minutes

## Requirements
- You have an attitude to learn while having fun :)
- You have some programming experience with Java, Spring and Spring Boot
- You have a valid debit or credit card to create a AWS Account
- AWS Fargate is not in FREE Tier. You are responsible for monitoring the usage of AWS resources to reduce your billing.
- You DO NOT need to have any experience with AWS (Amazon Web Services)


## Step By Step Details

### Getting Started with the course

- Step 00 - Deploying Spring Boot Microservices with ECS and AWS Fargate - Introduction
- Step 01 - Deploying Spring Boot Microservices with ECS and AWS Fargate - Getting Started with the Course

### Getting Started with AWS

- Step 01 - Creating an AWS Root Account
- Step 02 - Creating an IAM User for your AWS Account

### Getting Started with ECS and AWS Fargate

- Step 01 - 10000 Feet Overview of Cloud, AWS, ECS and AWS Fargate
- Step 02 - Getting Started with AWS Fargate - Deploy First Docker Container to AWS
- Step 03 - Launch Sample App in Browser
- Step 04 - Understanding AWS ECS Task Definition - Create first task definition for Hello World API
- Step 05 - Create an ECS Service for Hello World API
- Step 06 - Explore ECS Services - Launch Hello World REST API in Browser
- Step 07 - Its Your Responsibility to Monitor Billing on the Cloud - 5 Recommendations
- Step 08 - Monitor AWS Billing - Setting Billing Alerts
- Step 09 - Reduce Bills - Set Service Desired Tasks to 0
- Step 10 - Launch an AWS Fargate Task Directly
- Step 11 - Run Batch Programs Using ECS Tasks - Simple Spring Task

### Getting Started with ECS with EC2 instances

- Step 01 - Getting Started with ECS with EC2 instances
- Step 02 - Create a Cluster for ECS with EC2 instances
- Step 03 - Create a Service - Introduction to Region and AZ
- Step 04 - Using Host Network Mode - Create New Task Definition and Service
- Step 05 - ECS Cluster Management - Scale EC2 Instances
- Step 06 - Debugging ECS and Fargate Problems using Service Events
- Step 07 - Debugging Problems - Container Status, Stopped Reason and Cloud Watch Logs
- Step 08 - Clean up Cluster
- Step 09 - ECS with EC2 vs AWS Fargate

### Introduction to Microservices

- Step 01 - Introduction to Microservices
- Step 02 - Microservices - Challenges
- Step 03 - Advantages of Microservices

### Deploying Currency Exchange Microservice with H2 to AWS Fargate

- Step 01 - Import Microservices Projects into Eclipse
- Step 02 - Code Review and Running Currency Exchange Microservice on Local
- Step 03 - Build and Run Docker Container Image on Local for Currency Exchange Microservice
- Step 04 - Push Currency Exchange Microservice Docker Image to Docker Hub
- Step 05 - Currency Exchange Microservice with H2 - Create Task Definition and Service
- Step 06 - Execute Currency Exchange Microservice on AWS
- Step 07 - Using Task Metadata service to populate environment information
- Step 08 - Exercise - Build Container Images for Hello World and Spring Task Projects

### Deploying Currency Exchange Microservice with MySQL to AWS Fargate

- Step 00 - Quick Tip - Use Prebuild Container Images from Docker Hub
- Step 01 - Currency Exchange Microservice with MySQL - Code Review
- Step 02 - Launch MySQL as a Docker Container in Local
- Step 03 - Connect Local Currency Exchange Microservice with MySQL
- Step 04 - Create Currency Exchange Microservice with MySQL Docker Image and Push to Docker Hub
- Step 05 - Create MySQL Database on AWS using RDS
- Step 06 - Create Task Definition and Service for Currency Exchange Microservice with MySQL
- Step 07 - Configure Security Group to all traffic from Microsevice to RDS Database
- Step 08 - Creating a New Security Group for RDS Database

### Deploying Currency Conversion Microservice to AWS Fargate

- Step 01 - Code Review and Running Currency Conversion Microservice on Local
- Step 02 - Create Docker Image for Currency Conversion Microservice and Connect to Exchange Microservice using same network
- Step 03 - Deploy Currency Conversion Microservice to AWS Fargate - Create ECS Task Definition and Service
- Step 04 - Execute Currency Conversion Microservice on AWS Fargate
- Step 05 - Running Currency Conversion Microservice and Currency Conversion Microservice in Same Task - Part 1
- Step 06 - Running Currency Conversion Microservice and Currency Conversion Microservice in Same Task - Part 2

### Implement Centralized Configuration Management with AWS Parameter Store

- Step 01 - Introduction to Centralized Configuration Management with AWS Parameter Store
- Step 02 - Update ECS Task Definitions to use Centralized Configuration
- Step 03 - Provide access to Parameter Store for ECS Task Execution Role and Test the REST API
- Step 04 - Centralized Configuration Management - A Review

### Implement Distributed Tracing with AWS X Ray

- Step 01 - Introduction to Distributed Tracing with AWS X Ray - Code Review
- Step 02 - Deploy Currency Exchange Microservice with AWS X Ray - Update Task Def and Service
- Step 03 - Provide access to AWS X Ray for ECS Task Execution Role
- Step 04 - Currency Conversion Microservice with X Ray - Code Review and Deployment to AWS
- Step 05 - CCS and CES in X Ray - Service Maps, Traces and Analytics

## Implement Auto Scaling and Load Balancing with AWS Fargate

- Step 01 - Introduction to Auto Scaling and Load Balancing - Create ELB
- Step 02 - Deploy New Load Balanced Service for Currency Exchange Microservice
- Step 03 - Testing Currency Exchange Microservice through Load Balancer
- Step 04 - Deploy New Load Balanced Service for Currency Conversion Microservice
- Step 05 - Testing Currency Conversion Microservice through Load Balancer
- Step 06 - Configure Auto Scaling for Currency Exchange Microservice
- Step 07 - AWS Fargate Auto Scaling in Action - Scale in and Scale out
- Step 08 - Clean Resources from this section
- Step 09 - Tracing Request across Microservices using AWS CloudWatch Log Groups

## Deploying Service Mesh using AWS App Mesh

- Step 01 - Introduction to AWS App Mesh
- Step 02 - Introduction to Service Discovery and Implementing Service Discovery for Currency Exchange Microservice
- Step 03 - Implementing Service Discovery for Currency Conversion Microservice - Review Hosted Zones and DNS
- Step 04 - Creating EC2 Instance in VPC for Service Discovery Testing
- Step 05 - SSH into EC2 Instance and Run Microservices using curl
- Step 06 - Quick Tip - Format using jq
- Step 07 - Basics of AWS App Mesh - Mesh, Virtual Nodes and Virtual Services
- Step 08 - Integrate Microservics with AWS App Mesh - Update ECS Task Definitions and Service
- Step 09 - Review AWS App Mesh - Cloud Watch Logs and X Ray Tracing
- Step 10 - App Mesh and X Ray - Zero Microservice Code Change Needed - Deploy Non X Ray Microsevices to AWS Fargate
- Step 11 - Canary Deployments with AWS AppMesh - Deploy Multiple Version of Microservice - 1
- Step 12 - Canary Deployments with AWS AppMesh - Deploy Multiple Version of Microservice - 2
- Step 13 - Clean Up Services and Hosted Zones

## Implement CI and CD with AWS Code Pipeline

- Step 01 - Overview - CI and CD with AWS Code Pipeline - Create ECR Repository
- Step 02 - Commit Hello World REST API Project to GIT Repository
- Step 03 - Updating Application Specification for ECS Deployment with Task Definition and Container Name
- Step 04 - Create Code Pipeline for Hello World REST API
- Step 05 - Provide Access to ECR to Code Build Role
- Step 06 - Understanding Code Build Specification - Build Spec
- Step 07 - Review of AWS Code Pipeline

## Diagrams

- Courtesy http://viz-js.com/

```
graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB;
node[shape=record, width=2]

1 -- 2 -- 3 -- 4

1[label=<Cluster>]
2[label=<Service>]
3[label=<Task>]
4[label=<Container>]

}

digraph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB;
node[shape=record]
CO[shape=record, width=6.5, style=filled,color="#D14D28", fontcolor=white]
CI,CC[shape=record, width=2]
CL[shape=record, width=6]
2,3,4[shape=record, width=1.5]
CI -> CO
CC -> CO
CO -> CL
CL -> 2 
CL -> 3
CL -> 4

CI[label=<Container Image(s)>]
CO[label=<Container Orchestrator>]
CC[label=<Configuration>]
CL[label=<Cluster>]
2[label=<Virtual Server 1>]
3[label=<Virtual Server 2>]
4[label=<Virtual Server 3>]

}

digraph architecture {

node[style=filled,color="#36BF80"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB;
node[shape=record]
CO[shape=record, width=6.5, style=filled,color="#D14D28", fontcolor=white]
CI,CC[shape=record, width=2]
CL[shape=record, width=6]
2,3,4[shape=record, width=1.5]
CI -> CO
CC -> CO
CO -> CL
CL -> 2
CL -> 3
CL -> 4

CI[label=<Container Image(s)>]
CO[label=<ECS>]
CC[label=<Configuration>]
CL[label=<Cluster>]
2[label=<EC2 Instance1>]
3[label=<EC2 Instance2>]
4[label=<EC2 Instance3>]
}

digraph architecture {

node[style=filled,color="#36BF80"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB;
node[shape=record]
CO[shape=record, width=6.5, style=filled,color="#D14D28", fontcolor=white]
CI,CC[shape=record, width=2]
CL[shape=record, width=6,color="#59C8DE"]
CI -> CO
CC -> CO
CO -> CL

CI[label=<Container Image(s)>]
CO[label=<AWS Fargate>]
CC[label=<Configuration>]
CL[label=<Cluster>]
}

digraph architecture {
  rankdir=TB;
{rank=same; CurrencyConversionService, CurrencyExchangeService};
Database[shape=cylinder]
CurrencyConversionService, CurrencyExchangeService[shape=component]

  CurrencyConversionService -> CurrencyExchangeService;
  
  CurrencyExchangeService->Database;
  
  CurrencyConversionService[label=<Currency Conversion Service <BR /><BR /><FONT POINT-SIZE="8">10 USD = 600 INR<BR /><BR /><BR /></FONT>>];
  CurrencyExchangeService[label=<Currency Exchange Service <BR /><BR /><FONT POINT-SIZE="8">1 USD = 60 INR<BR />1 EUR = 70 INR<BR/>1 AUD = 50 INR</FONT>>];

}

graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB

node[shape=record, width=0.5]
LoadBalancer[width=5,label=<Load Balancer>,color="#D14D28", fontcolor=white]
Listener1[width=3,label=<Listener <BR />>]
Listener2[width=3,label=<Listener ..>]
TG1,TG2[width=2.5,label=<Target Group <BR />>]
Rule1[width=2.75,label=<Rule<BR /><FONT POINT-SIZE="11">/api/currency-conversion-microservice</FONT>>]
Rule2[width=2.75,label=<Rule<BR /><FONT POINT-SIZE="11">/api/currency-exchange-microservice</FONT>>]
CCS1,CCS2,CCS3[label=<Conversion <BR/>Service>]
CES1,CES2[label=<Exchange <BR/>Service>]
LoadBalancer -- Listener1
LoadBalancer -- Listener2
Listener1 -- Rule1
Listener1 -- Rule2
Rule1 -- TG1
Rule2 -- TG2
TG1 -- CCS1
TG1 -- CCS2
TG1 -- CCS3
TG2 -- CES1
TG2 -- CES2

}

graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB;
node[shape=record]

IAM -- Console
Console -- UserID
Console -- Password
IAM -- Application
Application -- AccessKeyID
Application -- SecretAccessKey

IAM[label=<IAM User>]
AccessKeyID[label=<Access Key Id>]
UserID[label=<User Id>]
SecretAccessKey[label=<Secret Access Key>]
Console[label=<Management Console>]
Application[label=<APIs>]
}

graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB;
node[shape=record]

Users -- RootUser
Users -- IAM

RootUser[label=<Root User>]
IAM[label=<IAM User>]

}



graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB
node[shape=record, width=2]
edge [width=0]
graph [pad=".75", ranksep="0.05", nodesep="0.25"];

Applications -- Software [style=invis]
Software -- OS [style=invis]
OS -- Hardware [style=invis]

}


graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB
node[shape=record, width=3]

Containers, LocalImages [height=1]

DockerClient -- Daemon
Daemon -- Containers 
Daemon -- LocalImages
Daemon -- ImageRegistry

DockerClient[label=<Docker Client>]
ImageRegistry[label=<Image Registry <BR /><FONT POINT-SIZE="10">nginx<BR />mysql<BR />eureka<BR />your-app<BR /><BR /></FONT>>];
Daemon[label=<Docker Daemon>]


}


graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB
node[shape=record, width=2]
Hypervisor,HostOS, Hardware[shape=record, width=6.5, style=filled,color="#D14D28", fontcolor=white]
edge [width=0]
graph [pad=".75", ranksep="0.05", nodesep="0.25"];

Application1 -- Software1 [style=invis]
Application2 -- Software2 [style=invis]
Application3 -- Software3 [style=invis]

Software1 -- GuestOS1 [style=invis]
Software2 -- GuestOS2 [style=invis]
Software3 -- GuestOS3 [style=invis]
GuestOS1 -- Hypervisor [style=invis]
GuestOS2 -- Hypervisor [style=invis]
GuestOS3 -- Hypervisor [style=invis]
Hypervisor -- HostOS [style=invis]
HostOS -- Hardware [style=invis]

}


graph architecture {

node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28", fontcolor=white];
rankdir = TB
node[shape=record, width=2]
HostOS, CloudInfrastructure, DockerEngine[shape=record, width=6.5, style=filled,color="#D14D28", fontcolor=white]
edge [width=0]
graph [pad=".75", ranksep="0.05", nodesep="0.25"];
Container1,Container2,Container3[height=2]

Container1 -- DockerEngine [style=invis]
Container2 -- DockerEngine [style=invis]
Container3 -- DockerEngine [style=invis]
DockerEngine -- HostOS [style=invis]
HostOS -- CloudInfrastructure [style=invis]

}
```

### Troubleshooting
- Refer our TroubleShooting Guide - https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ

## Youtube Playlists - 500+ Videos

[Click here - 30+ Playlists with 500+ Videos on Spring, Spring Boot, REST, Microservices and the Cloud](https://www.youtube.com/user/rithustutorials/playlists?view=1&sort=lad&flow=list)

## Keep Learning in28Minutes

in28Minutes is creating amazing solutions for you to learn Spring Boot, Full Stack and the Cloud - Docker, Kubernetes, AWS, React, Angular etc. - [Check out all our courses here](https://github.com/in28minutes/learn)
