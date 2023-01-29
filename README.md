# E Commerce Application
 


## **WEB SERVICE : RESTFUL A.P.I**

RESTful API for e-commerce, engineered utilizing the Spring Boot framework and the Java programming language. This API adheres to the principles of RESTful architecture and is designed with scalability and maintainability at the forefront, allowing for effortless integration with third-party applications.

A salient feature of this API is its support for image management, which encompasses uploading and serving for customer, product, review, and feedback entities, as well as the ability to delete images from the database using HTTP methods, enabling optimal space management on both the server and client side.

This API boasts a highly modular and adaptable architecture, utilizing a sophisticated model mapping mechanism for seamless integration between entities and Data Transfer Objects (DTOs). This allows for a streamlined development process and a consistent, user-friendly experience through the implementation of custom DTOs for HTTP requests and responses, as well as JSON API responses for all requests.

Security is a top priority for this API, incorporating Spring Security and JSON Web Tokens (JWT) to ensure secure user authentication and role-based access control for each HTTP request, guaranteeing that only authorized users can access sensitive data. Moreover, the API employs Hibernate Validator, a powerful validation framework, to enforce data integrity and constraints across all HTTP requests and request bodies, thereby ensuring the validity and consistency of data throughout the application, and providing custom validation exceptions.

To enhance the user experience, the API incorporates Spring Hypermedia as the Engine of Application State (HATEOAS) library to provide hypermedia links, which aids in user flow and ease of access. Users can also take advantage of the API's pagination, filtering, sorting, and searching functionality, with self, collections, and paging hypermedia links, for improved efficiency. A system-generated admin interface is also provided for ease of use.

With its focus on performance, security, and user-friendliness, this API represents a cutting-edge solution for integrating e-commerce functionality into any application. Whether you are a developer seeking to add e-commerce capabilities to your existing application or a business looking for a powerful and customizable e-commerce platform, this API is the optimal choice.


The API's Services  will be used by the **two** categories of users:

- **Admin (Administrator)**

- **User (Customer)**

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

## Administrator Side Features

 - **Administrator Management** 

   - Endpoint for Admin Sign Up
   - Endpoint for Updating Admin Information
   - Endpoint for Deleting Admin Accounts
   - Endpoint for Retrieving Admin Information

 - **Product Management**

   - Endpoint for Adding Products
   - Endpoint for Updating Product Information
   - Endpoint for Deleting Product Information
   - Endpoint for Updating Product Rating
   - Endpoint for Updating Product Stock Quantity
   - Endpoint for Updating Product Image
   - Endpoint for Changing Product's Category
   - Endpoint for Adding Products to Discount Sales
   - Endpoint for Revoking Product Availability
   - Endpoint for Adding Products to Customer's Best Choice

 - **Order Management**

   - Endpoint for Updating Order Status( Processed, Failed , To be Dispatched, Out For Delivery, Returned, Replaced )
   - Endpoint for Searching Orders by User Details
   - Endpoint for Searching Orders by Order Information
   - Endpoint for Retrieving Order Information
   - Endpoint for Approving Refund Requests
   - Endpoint for Approving Replacement Requests
   - Endpoint for Retrieving Cancelled/Refunded Orders
   
 - **Payment Management**

   - Endpoint for Adding Payment Methods
   - Endpoint for Updating Payment Methods
   - Endpoint for Deleting Payment Methods
   - Endpoint for Revoking Payment Methods

 - **Customer Management**

   - Endpoint for Retrieving Customer Information
   - Endpoint for Searching Customers by:
     - First Name
     - Last Name
     - First & Last Name
     - Email ID

 - **Feedback & Reviews**

   - Endpoint for Retrieving Feedback & Reviews
   - Endpoint for Sorting Feedback & Reviews by:
     - Rating
     - Date
     - Customer
     - Order

 - **Dashboards**

   - Endpoint for Retrieving Sales Information:
     - Today's Sales
     - Last Week's Sales
     - Last Month's Sales
     - Sales from Jan to Dec
   - Endpoint for Retrieving Product Information:
     - Highest Sold Product by Rating in a Given Duration
     - Highest Rated Product in a Given Duration
     - Highest Sold Product Categorized by Sale Price
     - Highest Sold Product in Different Categories



## User Side Features

 - **Customer Management** 

   - Endpoint for Sign Up
   - Endpoint for Updating Customer Information
   - Endpoint for Deleting Customer Accounts
   - Endpoint for Retrieving Customer Information
   - Endpoint for Updating Customer Profile Pictures
   - Endpoint for Deleting Customer Profile Pictures
 
 - **Address Management**
  
   - Endpoint for Adding Addresses
   - Endpoint for Retrieving Customer Addresses
   - Endpoint for Updating Customer Addresses
    
 - **Cart Management** 
   - Endpoint for Adding Products to Cart
   - Endpoint for Updating Product Quantity in Cart
   - Endpoint for Emptying the Cart
   - Endpoint for Deleting Products from Cart
   - Endpoint for Retrieving Cart Details
   - Endpoint for Processing a Cart Purchase
   
- **Category Management** 
   - Endpoint for Retrieving Categories
   - Endpoint for Retrieving Products by Category
   - Endpoint for Sorting Products by Category Information
   - Endpoint for Searching Categories
   
- **Feedback & Reviews** 

   - Endpoint for Adding Feedback (with Image Attachment Option)
   - Endpoint for Adding and Deleting Product Reviews and Ratings
   - Endpoint for Sorting Reviews by:
     - Added Date
     - Rating   
   
- **Payment** 

   - Endpoint for Retrieving Available Payment Methods

- **Product Management** 

   - Endpoint for Retrieving Product Details
   - Endpoints for Sorting Products by:
     - Rating
     - Sale Price
     - Sale Availability
     - Popularity among Customers
     - Manufacturing Month/Year
     - Addition Date
     - Addition Date by Category
     - Rating within a Category
    - Endpoints for Searching Products by:
      - Name
      - Type
     
       
- **Order Management**

   - Endpoint for Placing Orders
   - Endpoint for Retrieving Order History
   - Endpoint for Cancelling Orders
   - Endpoint for Requesting Refunds/Replacements
   - Endpoint for Tracking Order/Refund/Replacement Status
    



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
https://github.com/TejasMedade/E-Commmerce-Application
```

Open MySQL Server
```bash
Create a New Database in SQL: "e_commerce" 
```

System Generated Admin For Your Database

```bash
{
    "username": "9307710594",
    "password": "Tejas@1998"
}
```

## Important Note


- When utilizing the PostMan software, ensure that the request includes embedded cookies as JWT authentication is implemented as a cookie-based authentication mechanism.

- The roles have already been established within the database. Ensure that the appropriate requests are executed for both the Admin and User roles.

- It's important to note that an Admin is also considered as a User. Only Users with Admin privileges have the ability to create additional Admins within the database.

- The expiration time limit for JWT tokens is 20 minutes. Subsequently, a new login session is required after the 20-minute duration.
- An additional 2% discount will be applied to the sale price of a product when it is added to a discount sale.

- **Order cancellation policy**
   - Order cancellation is only possible if the order has not yet been marked as delivered.
- **Return policy**
   - Orders that have been marked as delivered by the admin cannot be cancelled and must be returned.
- **Refund and replacement policy**
   - Refunds and replacements for orders can only be processed once the return order and its status have been marked as picked up by the admin.


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



## Demo





## URL
```bash
http://localhost:8088
```
## Admin Login Logout API Reference


## Customer Login Logout API Reference


## Admin API Reference



## Customer API Reference


## Product API Reference


## Address API Reference


## Cart API Reference


## Order API Reference


## Feedback API Reference


## Review API Reference


## Contributions

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **Greatly Appreciated**.

If you have any ideas on how to improve this resume, please feel free to fork the repository and submit a pull request. Your contributions, no matter how big or small, are greatly appreciated and will help to make this repository even better.

In addition to contributing to the repository, you can also connect with me for further development and collaboration on this API. Together, we can continue to improve and evolve the API to meet the needs of the community.

We encourage you to give the repository a star and we thank you for your interest in this project. 

Your support is greatly appreciated.

## 🔗 Contact Me
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://tejasmedade.github.io/)

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/TejasMedade)

[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/TejasMedade)

## Authors

- [Tejas Vilas Medade](https://github.com/tejasmedade)

## Acknowledgements

- [Masai School](https://www.masaischool.com/)
 
