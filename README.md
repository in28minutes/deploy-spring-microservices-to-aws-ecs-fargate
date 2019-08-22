# Deploy Spring Boot and Docker Microservices to AWS using ECS Fargate

## Take your first steps towards cloud with AWS ECS Fargate. Deploy REST APIs and Microservices with Spring Boot and Docker Containers to the cloud.

Spring Boot is the No 1 Java Framework to develop REST API and Microservices. AWS (Amazon Web Services) is the No 1 Cloud Service Provider today.

How about learning AWS by deploying Spring Boot Docker Containers to Cloud using ECS Fargate?

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

### Setting up App Mesh

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

### Deploying Version 2 of Currency Exchange Service to ECS and App Mesh

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

## Installation Guides

#### Required Tools

- Java 8+
- Eclipse - Oxygen+ - (Embedded Maven From Eclipse)
- Git
- Docker

#### Installing Guides

- [Playlist - Installing Java, Eclipse & Embedded Maven](https://www.youtube.com/playlist?list=PLBBog2r6uMCSmMVTW_QmDLyASBvovyAO3)
- [Playlist - Installing Node Js (npm) & Visual Studio Code](https://www.youtube.com/playlist?list=PLBBog2r6uMCQN4X3Aa_jM9qVjgMCHMWx6)

#### Troubleshooting Installations
- Eclipse and Embedded Maven
  - Troubleshooting Guide : https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#tip--troubleshooting-embedded-maven-in-eclipse
  - PDF : https://github.com/in28minutes/SpringIn28Minutes/blob/master/InstallationGuide-JavaEclipseAndMaven_v2.pdf
  - GIT Repository For Installation : https://github.com/in28minutes/getting-started-in-5-steps

## Course Overview

In this course, we deploy a variety of Spring Boot Applications to the Cloud:
- REST APIs - Hello World and CCS and CES - Jar
- Single Container with REST API
- Multi Container with Multiple REST APIs

This course would be a perfect first step as an introduction to AWS and the Cloud.

You will learn about automating deployments and creating a continuous delivery pipeline with AWS Code Pipeline. You will learn how to Auto Scale applications based on load as well as deploy multiple instances behind a load balancer using AWS ECS Fargate.

You will be using a number of AWS Services - ECS, Fargate, EC2, S3, AWS CodePipeLine, AWS CodeBuild, IAM, CloudWatch, ELB, Target Groups, X Ray. 

You will be using deploying a variety of projects to Amazon Web Services (AWS). These projects are created with  Spring Boot (REST API Framework), Spring (Dependency Management), Maven (dependencies management), Eclipse (Java IDE) and Tomcat Embedded Web Server. We will help you set up each one of these.

## What you'll learn
- You will Learn the Fundamentals of Amazon Web Services from Zero, no previous experience required
- You will learn to deploy Spring Boot REST API to AWS with AWS Elastic Beanstalk
- You will learn to deploy Java, Spring Boot Full Stack Applications to AWS with AWS Elastic Beanstalk and S3
- You will be using a number of AWS Services - EC2, S3, AWS CodePipeLine, AWS CodeBuild, SQS, IAM, CloudWatch. 
- You will learn to deploy containerized Java Spring Boot applications to AWS
- You will learn to create a continuous delivery pipeline with AWS Code Pipeline
- You will learn how to Auto Scale applications based on load as well as deploy multiple instances behind a load balancer using Elastic Beanstalk.
- You will Join 250,000 Learners having AMAZING LEARNING Experiences with in28Minutes

## Requirements
- You have an attitude to learn while having fun :)
- You have some programming experience with Java, Spring and Spring Boot
- You have a valid debit or credit card to create a AWS Account
- You DO NOT need to have any experience with AWS (Amazon Web Services)


## Step By Step Details

### Getting Started with the course
- Step 00 - Deploying Spring Boot Applications with AWS Elastic Beanstalk - Introduction
- Step 01 - Getting Started with the Course

### Getting Started with AWS
- Step 01 - Creating an AWS Root Account
- Step 02 - Creating an IAM User for your AWS Account

### Getting Started with AWS Elastic Beanstalk
- Step 01 - 10000 Feet Overview of Cloud, AWS and Elastic Beanstalk
- Step 02 - Getting started with AWS Elastic Beanstalk - Creating First Application and Environment
- Step 03 - Quick Exploration of AWS Elastic Beanstalk
- Step 04 - Quick Introduction to AWS Regions
- Step 05 - AWS Resources created by Elastic Beanstalk - An Overview
- Step 06 - Its Your Responsibility to Monitor Billing on the Cloud - 5 Recommendations
- Step 07 - Monitor AWS Billing - Setting Billing Alerts

### Deploying Java Spring Boot Hello World REST API to AWS Elastic Beanstalk
- Step 01 - Importing Seven Spring Boot Projects from Github
- Step 02 - Quick Tip - Use Artifacts from build artifacts folder
- Step 03 - Running 01 Spring Boot Hello World Application in Local
- Step 04 - Building jar for 01 Spring Boot Hello World Application
- Step 05 - Deploy 01 Spring Boot Hello World Application to AWS Elastic Beanstalk

### Deploying Java Spring Boot Todo REST API to AWS Elastic Beanstalk
- Step 01 - Setting up 02 Spring Boot Todo REST API in Local
- Step 02 - Testing 02 Spring Boot Todo REST API in Local
- Step 03 - Build and Deploy 02 Spring Boot Todo REST API in AWS Elastic Beanstalk
- Step 04 - Exploring AWS Elastic Beanstalk Application Versions
- Step 05 - Exploring Termination of AWS Elastic Beanstalk Application Environments
- Step 06 - Exploring Logs from AWS Elastic Beanstalk Application Java JAR Environments

### Deploying Java Spring Boot H2 Web App to AWS Elastic Beanstalk
- Step 01 - Running 03 Spring Boot H2 Web App on Local
- Step 02 - Deploying 03 Spring Boot H2 Web App WAR to AWS Elastic Beanstalk
- Step 03 - Testing 03 Spring Boot H2 Web App AWS Deployment
- Step 04 - Exploring AWS Environment logs of 03 Spring Boot H2 Web App

### Deploying Java Spring Boot Web App talking to MySQL with AWS Elastic Beanstalk and AWS RDS
- Step 01 - Code Review of 04 Spring Boot MySQL Web App - Environment Variables
- Step 02 - Running MySQL as Docker Container on Local
- Step 03 - Connect 04 Spring Boot MySQL Web App to MySQL on Local
- Step 04 - Deploy 04 Spring Boot MySQL Web App to AWS Elastic Beanstalk
- Step 05 - Testing 04 Spring Boot MySQL Web App Deployment on AWS Elastic Beanstalk
- Step 06 - Exploring AWS Relational Database Service - RDS
- Step 07 - Exploring AWS - Understanding Security Groups
- Step 08 - Creating AWS RDS Database outside AWS Elastic Beanstalk
- Step 09 - Setting up 04 Spring Boot MySQL Web App to connect to RDS - Environment Variables and Security Groups
- Step 10 - Creating new AWS Security Group for AWS Elastic Beanstalk Application
- Step 11 - Exploring AWS Elastic Beanstalk Save Environment Configuration Feature

### Deploying Java Full Stack Spring Boot React App with AWS Elastic Beanstalk and AWS S3
- Step 01 - Exploring 05 Java Full Stack Spring Boot React App
- Step 02 - Running React Frontend in Local
- Step 03 - Deploying Java REST API Backend to AWS Elastic Beanstalk
- Step 04 - Building React Frontend Code for AWS Deployment
- Step 05 - Quick Introduction to AWS Simple Storage Service - S3
- Step 06 - Deploying React Frontend to AWS S3 Static Website

### Deploying Containerized Java Applications with AWS Elastic Beanstalk
- Step 01 - Code Review for 06 Single Container Java Spring Boot REST API
- Step 02 - Creating and Running Local Docker Image for 06 Java Spring Boot REST API
- Step 03 - Pushing 06 Single Container Java Spring Boot REST API Docker Image to Docker Hub
- Step 04 - Deploying 06 Single Container Java Spring Boot REST API Image to AWS Beanstalk
- Step 05 - Running 07 Multi Container Java Spring Boot REST API with MySQL on Local
- Step 06 - Deploying 07 Multi Container Java Spring Boot REST API with MySQL to AWS Beanstalk
- Step 07 - Testing 07 Multi Container Java Spring Boot REST API with MySQL on AWS - Security Groups

### AWS Elastic Beanstalk - CLI, Blue Green Deployments and Worker Apps
- Step 01 - Introduction to AWS Elastic Beanstalk Command Line Interface EB CLI
- Step 02 - Exploring AWS Elastic Beanstalk Command Line Interface EB CLI - Commands
- Step 03 - Exploring AWS Elastic Beanstalk Blue Green Deployments
- Step 04 - Creating and Deploying Java Worker App to AWS Elastic Beanstalk
- Step 05 - Testing Java Worker App on AWS Elastic Beanstalk using SQS - Simple Queing Service

### AWS Elastic Beanstalk - Creating Load Balanced Auto Scaling Environments
- Step 01 - Creating Load Balanced Auto Scaling Environments with AWS Elastic Beanstalk
- Step 02 - Understand Magic of AWS Elastic Beanstalk - AZ, ELB, ASG and CloudWatch Alarms
- Step 03 - Playing with AWS Elastic Beanstalk Auto Scaling Features
- Step 04 - Playing with AWS Elastic Beanstalk Rolling Deployment Options - 1
- Step 05 - Playing with AWS Elastic Beanstalk Rolling Deployment Options - 2

### Continuous Delivery for Java Spring Boot Applications with AWS CodePipeline and AWS CodeBuild
- Step 01 - Getting Started with AWS CodePipeline and AWS CodeBuild
- Step 02 - Creating Github Repo and Commit Spring Boot Java Project
- Step 03 - Configuring AWS CodeBuild and AWS CodePipeline
- Step 04 - Creating AWS CodeBuild Build Specification 
- Step 05 -  Testing Continuous Deployment with AWS CodePipeline

### Email headings
  - ⚡⚡⚡ [NEW COURSE] Deploy Spring Boot to Cloud - Amazon Web Services
  - Is it Your Time... (to start with aws)
  - By Far, The Easiest Way? ... (to learn cloud)
  - #1 Skill For Years to Come
  - Do You have the #1 Skill for a Java Developer?
  - Do You want to be Future Proof?

### Email Template

```
Do you have ZERO experience with Amazon Web Services (AWS)? 

Are you ready to learn about the cloud with Amazon Web Services (AWS) and take the next step in your programming career? 

Do you want to learn to Deploy Java Spring Boot Applications to Amazon Web Services (AWS) using AWS Elastic Beanstalk with an easy to learn, step by step approach? 

Do you want to join 300,000+ learners having Amazing Learning Experiences with in28Minutes?

For existing in28Minutes Learners, we are creating a special offer for just $9.99. This is the LOWEST possible price allowable on Udemy. 

As always, you receive a full 30-day, unconditional money back guarantee because I know you are going to love our courses!

https://www.udemy.com/deploy-java-spring-boot-to-aws-amazon-web-service/?couponCode=FIRST_PROMO_UDEMY

This course would be a perfect first step as an introduction to AWS and the Cloud.

You will learn about automating deployments with Elastic Beanstalk CLI and creating a continuous delivery pipeline with AWS Code Pipeline. You will learn how to Auto Scale applications based on load as well as deploy multiple instances behind a load balancer using Elastic Beanstalk.

You will be using a number of AWS Services - EC2, S3, AWS CodePipeLine, AWS CodeBuild, SQS, IAM, CloudWatch. 

You will be using deploying a variety of projects to Amazon Web Services (AWS). These projects are created with  React (Frontend Framework), Spring Boot (REST API Framework), Spring (Dependency Management), Spring Security (Authentication and Authorization - Basic and JWT), BootStrap (Styling Pages), Maven (dependencies management), Node (npm), Visual Studio Code (TypeScript IDE), Eclipse (Java IDE) and Tomcat Embedded Web Server. We will help you set up each one of these.

https://www.udemy.com/deploy-java-spring-boot-to-aws-amazon-web-service/?couponCode=FIRST_PROMO_UDEMY


Happy coding and thanks for being a student!


Ranga

Thank you for all the love!

#in28Minutes #ImLearningIn28Minutes #ImLovingIn28Minutes

Good Luck and Keep Learning in28Minutes
```

```sh
for file in *; do mv "${file}" "${file//-/ }"; done
for file in *; do mv "${file}" "${file//   / - }"; done
for file in *; do mv "${file}" "${file//01 Step/Step}"; done
```

  
### Useful Links
- [Our Website](http://www.in28minutes.com)
- [Facebook](http://facebook.com/in28minutes)
- [Twitter](http://twitter.com/in28minutes)
- [Google Plus](https://plus.google.com/u/3/110861829188024231119)

## Diagrams

- Courtesy http://viz-js.com/

```
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

## Todo
- Course Creation
  - 3 Videos - Intro, Best Use and Conclusion
  - Intro to Cloud, AWS, Containers and ECS
  - Cost Review
  - Exercise - Hello World and First Task Projects
  - Container Images - Prebuilt
  - EC2 vs Fargate
  - Clean Up

- Post Course Creation
  - Course Promotion Emails/Posts
    - 1 Emails on Udemy
    - 1 Emails to Email List
  - Create YouTube Course Preview Video
    - Add YouTube Course Preview Video as End Video for all videos
    - Make it the YouTube Default Video
  - Release atleast 20 small videos - one a day on Youtube
  - Do atleast 3 Youtube live sessions
  - After a Month
    - UFB and Packt

## Next Steps

- Free Tier and Billing Alerts everywhere
- Check Region before each video
    - AWS
      - SSH into EC2
      - Creating AWS Account
      - Creating IAM User
