# Tredara Online Auction Platform

## Table of Contents

- [About](#About)
- [Getting Started](#Getting-Started)
   - [JAR File](#JAR-File)
   - [Docker](#Docker)
   - [AWS](#Hosted-in-AWS)
- [Project Managment](#implementation-and-design)
   - [Architecture](#Architecture)
   - [Backlog and UserStory](#Backlog-and-UserStory)
   - [UseCase Diagram](#usecase-diagram)
   - [Model Diagram](#model-diagram)
   - [Class Diagram](#class-diagram)
   - [FrontEnd Diagram](#FrontEnd-Diagram)
- [Technologies and tools used in project](#Technologies-and-tools-used-in-project)
- [Collaboration](#collaboration)
- [Future improvement](#future-improvement)


## About
Tredara is a revolutionary online auction platform that aims to transform the secondhand market by bringing it to the internet.
This program is designed to sell items through bidding so that the customer who bids the most by the end date receives the product..

In this program, users are divided into two categories:

**Guest User:**
1. View all active items 
2. Search among items
3. View item details
4. Register in the platform

**Registered User:**
1. Add new products
2. Leave bids for other users' items
3. Get the wining notification email after wining an auction


## Getting Started

To get started with the Tredara project, follow these steps:
### JAR-File 
1. **Clone Repositories:**
- git clone github.com/AlirezaKToosi/Tredara.git
2. **Install Dependencies:**
- cd Tredara
- mvn install
3. **Run the JAR file:**
- java -jar ----------------------------------
### Docker
- cd Tredara
- docker compose ------------------------------

### Hosted in AWS
- Link - http://-----------

### DataBase
- Database(MySQL):
  - User : root
  - Password : -----------------------
  - Database Schema: tredara
  - Port : 3306

## Project Management

### Architecture :
![Architecture.jpg](documentation%2FArchitecture.jpg)

### Backlog and UserStory:
[Project Backlog](https://trello.com/invite/b/iVHDFoc8/ATTIfdad8d7bed847d9e7528afa6fd7ea46954EE087F/tredara)
### Usecase Diagram:
![Usecase_Diagram.jpg](documentation%2FUsecase_Diagram.jpg)

### Model Diagram:
![Model_Diagram.jpg](documentation%2FModel_Diagram.jpg)

### Class Diagram:
![Class_Diagram.png](documentation%2FClass_Diagram.png)

### FrontEnd Diagram:
![Frontend_Diagram.png](documentation%2FFrontend_Diagram.png)


### Log Files

The log files generated by the Tredara platform are stored in the `/logs` directory. You can access these logs for auditing and monitoring purposes.

## Technologies and tools used in project
1. Java 17 
2. Spring Boot 3.1.4
3. Maven
4. MySQL 8
5. React 
6. Docker 
7. AWS 
8. PostMan

## Collaboration
This is a team project, all parts of the project are developed collaboratively.

### Team Members
1. Olga Pinchuk
2. Rashmoni Dey
3. Rohit Agarwal
4. Alireza KafshdarToosi


## Future improvement
1. Administrator Panel
2. Payment Services
3. Direct Selling
