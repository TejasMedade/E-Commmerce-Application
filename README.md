# E Commerce Application
 

![Logo](https://nimbus-screenshots.s3.amazonaws.com/s/91e455fba18e9a844446391993ef3a15.png)


# E Commerce Application


 **REST API SERVICE**

RESTful API for e-commerce, engineered utilizing the Spring Boot framework and the Java programming language. This API adheres to the principles of RESTful architecture and is designed with scalability and maintainability at the forefront, allowing for effortless integration with third-party applications.

A salient feature of this API is its support for image management, which encompasses uploading and serving for customer, product, review, and feedback entities, as well as the ability to delete images from the database using HTTP methods, enabling optimal space management on both the server and client side.

This API boasts a highly modular and adaptable architecture, utilizing a sophisticated model mapping mechanism for seamless integration between entities and Data Transfer Objects (DTOs). This allows for a streamlined development process and a consistent, user-friendly experience through the implementation of custom DTOs for HTTP requests and responses, as well as JSON API responses for all requests.

Security is a top priority for this API, incorporating Spring Security and JSON Web Tokens (JWT) to ensure secure user authentication and role-based access control for each HTTP request, guaranteeing that only authorized users can access sensitive data. Moreover, the API employs Hibernate Validator, a powerful validation framework, to enforce data integrity and constraints across all HTTP requests and request bodies, thereby ensuring the validity and consistency of data throughout the application, and providing custom validation exceptions.

To enhance the user experience, the API incorporates Spring Hypermedia as the Engine of Application State (HATEOAS) library to provide hypermedia links, which aids in user flow and ease of access. Users can also take advantage of the API's pagination, filtering, sorting, and searching functionality, with self, collections, and paging hypermedia links, for improved efficiency. A system-generated admin interface is also provided for ease of use.

With its focus on performance, security, and user-friendliness, this API represents a cutting-edge solution for integrating e-commerce functionality into any application. Whether you are a developer seeking to add e-commerce capabilities to your existing application or a business looking for a powerful and customizable e-commerce platform, this API is the optimal choice.


The API's Services  will be used by the **two** categories of users:

- **Admin (Administrator)**

- **User (Customer)**
-  
## Tech Stack

- JAVA
- SPRING
- SPRINGBOOT
- HIBERNATE
- MAVEN
- J.D.B.C
- MYSQL
- POSTMAN

## Dependencies

- JWT AUTHENTICATION
- SPRING SECURITY
- SPRING HATEOAS
- SPRING DATA JPA
- SPRING BOOT DEVTOOLS
- SPRING WEB
- HIBERNATE
- MYSQL DRIVER
- VALIDATION
- LOMBOK
- MODEL MAPPER
- LOGGER


## System Structure

The REST API allows a Customer to **Signup, Login** & **Browse** through the products and add them to **Cart** wishlist, as well as the Administrator to **View** all the **Orders, Customers & Products** with filtering options. 

Administrator can **Add, Update, View, Delete** **Products** & other **Administrators** from the **Database**.
  

- Administrator
    -
    - Login & Logout
    - View all Products
    - View all Orders of an User
    - View all Customers
    - Add New Products into Database
    - Remove Products from Database
    - Update Products in Database 
    - Delete Admin from Database

- User / Customer
    -
    - Signup 
    - Login & Logout
    - Update all Personal Details and Address 
    - View all Products with filtering options
    - Add Products to Cart
    - Update Products Quantity in Cart 
    - Delete Products from Cart
    - Empty Cart
    - Add Order
    - Cancel Order
    - View Orders with filtering options
    - Delete Customer Account
    




## Setting & Installation 

Install the Spring Tools Suite 
```bash
https://spring.io/tools
```

Install MySQL Community Server

```bash
https://dev.mysql.com/downloads/mysql/
```

Clone the Repository

```bash
git clone https://github.com/TejasMedade/Online-Shopping-System
```

Open MySQL Server
```bash
Create a New Database in SQL: "Online_Shopping_System" 
```

Create a Admin For Your Database

```bash
INSERT INTO ADMIN VALUES('1001','admin_email','admin_first_name','admin_last_name','admin_mobile_number','admin_password');
```

Note for Admin & User
```bash
Admin Id : Min=1000, Max=1010 ; User Id : Enter Your Registered Mobile Number For Login, User Validation and Authentication. 
```




## Run Locally


Go to the Project Directory

```bas
Open the Online_Shopping_System Folder with S.T.S
```

Go to **src/main/resources > application.properties** & change your username and password (MySQL server username & password)

```bash
spring.datasource.username="username"

spring.datasource.password="password"
```

To change the **Server Port**

```bash
server.port=8088
```

Go to **com.masai package > Online_Shopping_System.java**

```bash
Run as Spring Boot App !
```
Open the following URL for Swagger-UI 
```bash
http://localhost:8088/swagger-ui/
```

## ER Diagram


![Online Shopping Application(1)](https://user-images.githubusercontent.com/105907169/202779904-80050caf-b679-4b67-9afe-da67296fcb59.png)




## Demo





## URL
```bash
http://localhost:8088
```
## Admin Login Logout API Reference
![image](https://user-images.githubusercontent.com/105907169/202855307-82cc63ca-7183-4785-8b30-31794d6dc7cb.png)

## Customer Login Logout API Reference
![image](https://user-images.githubusercontent.com/105907169/202855339-e5f872f6-e120-42cb-9fe8-432fb7ed9018.png)

## Admin API Reference

![Screenshot 2022-11-25 at 12-54-24 Swagger UI](https://user-images.githubusercontent.com/105907169/203924387-d053fa23-1a77-4c03-b33d-45d3007360d1.png)

## Customer API Reference
![image](https://user-images.githubusercontent.com/105907169/202855233-53a77b6f-dd18-4a74-b615-5aeb16f15da3.png)

## Product API Reference
![image](https://user-images.githubusercontent.com/105907169/202855428-2f93dc36-0e11-41b4-b592-5138eebd3e0b.png)

## Address API Reference
![image](https://user-images.githubusercontent.com/105907169/202855062-1588b3a0-caa6-46b5-accd-6759e86f2e98.png)

## Cart API Reference
![image](https://user-images.githubusercontent.com/105907169/202855032-4b7c5510-b6c3-44e0-b496-e8d52d571165.png)

## Order API Reference
![image](https://user-images.githubusercontent.com/105907169/202855388-da648cc9-bf27-42a8-bb2f-9e19f67f63b4.png)



## Contributions

Contributions are always **Welcome** !

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **Greatly Appreciated**.

If you have a suggestion that would make this REST API better, Please fork the repo and create a pull request. You can also connect with me for further development of this application !

Don't forget to give the project a star ! Thank You !

## 🔗 Contact Me
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://tejasmedade.github.io/)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/TejasMedade)

[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/TejasMedade)

## Authors

- [Tejas Vilas Medade](https://github.com/tejasmedade)

## Acknowledgements


- [Masai School](https://www.masaischool.com/)
 
