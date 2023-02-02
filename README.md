# E Commerce Application
 
![Untitled-2](https://user-images.githubusercontent.com/105907169/215356985-1c417023-35f5-41cc-beab-8a235d6d8cce.jpg)


## **WEB SERVICE : RESTFUL A.P.I.**

RESTful API for e-commerce, engineered utilizing the Spring Boot framework and the Java programming language. This API adheres to the principles of RESTful architecture and is designed with scalability and maintainability at the forefront, allowing for effortless integration with third-party applications.

A salient feature of this API is its support for image management, which encompasses uploading and serving for customer, product, review, and feedback entities, as well as the ability to delete images from the database using HTTP methods, enabling optimal space management on both the server and client side.

This API boasts a highly modular and adaptable architecture, utilizing a sophisticated model mapping mechanism for seamless integration between entities and Data Transfer Objects (DTOs). This allows for a streamlined development process and a consistent, user-friendly experience through the implementation of custom DTOs for HTTP requests and responses, as well as JSON API responses for all requests.

Security is a top priority for this API, incorporating Spring Security and JSON Web Tokens (JWT) to ensure secure user authentication and role-based access control for each HTTP request, guaranteeing that only authorized users can access sensitive data. Moreover, the API employs Hibernate Validator, a powerful validation framework, to enforce data integrity and constraints across all HTTP requests and request bodies, thereby ensuring the validity and consistency of data throughout the application, and providing custom validation exceptions.

To enhance the user experience, the API incorporates Spring Hypermedia as the Engine of Application State (HATEOAS) library to provide hypermedia links, which aids in user flow and ease of access. Users can also take advantage of the API's pagination, filtering, sorting, and searching functionality, with self, collections, and paging hypermedia links, for improved efficiency. A system-generated admin interface is also provided for ease of use.

With its focus on performance, security, and user-friendliness, this API represents a cutting-edge solution for integrating e-commerce functionality into any application. Whether you are a developer seeking to add e-commerce capabilities to your existing application or a business looking for a powerful and customizable e-commerce platform, this API is the optimal choice.




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




## Features

- **RESTful API Design** : Adheres to REST architectural principles, providing a set of CRUD endpoints accessible via standard HTTP methods (GET, POST, PUT, DELETE).
- **Modular Architecture** : Designed with well-defined modules, facilitating clear separation of concerns for easier maintenance and scalability.

- **Human-Readable URIs** : Uses user-friendly URIs for improved usability.

- **Image Handling** : Supports image upload and retrieval for customers, products, reviews, and feedback.

- **Data Mapping** : Employs object mapping to enable data exchange between client and server through mapping entities to transfer objects.

- **Hypermedia Navigation** : Integrates Spring HateOAS for facilitating user navigation through the provision of hypermedia links.

- **Data Retrieval** : Includes functionality for pagination, filtering, sorting, and searching for products, reviews, and feedback.

- **Efficient Data Transfer** : Uses optimized transfer objects to enhance performance in both HTTP requests and responses.

- **JSON API Responses** : Returns all requests in a JSON format, ensuring consistent data representation.

- **Customized Exception Handling** : Implements customized exception handling for improved user experience and error messaging.

- **Request Body Validation** : Implements request body data validation to ensure data accuracy and security.

- **Token-Based Auth and Authorization** : Enables authentication and authorization through the use of JSON Web Tokens (JWT) with Spring Security.

- **Role-Based Access Control** : Implements role-specific access control to restrict resource access to authorized users.

- **Security Measures** : Incorporates security measures such as JWT token expiration after 20 minutes and restricted admin registration to maintain system security.

- **Database Administration** : Provides administration tools for managing the database.

- **Product Categorization** : Implements product categorization for improved browsing experience.

- **Product Filtering** : Allows customers to view products based on the tag "Customer's Best Choice" and products on discount sale.

- **Order Management System** : Offers a platform for customers to place, view, cancel orders, make payments, and track order status including delivery, pickup, return, and replacement.

- **Payment Method Management** : Allows administrators to add, revoke, and manage available payment methods.

- **Sales and Revenue Reporting** : Enables generation and viewing of sales and revenue reports including top-selling and least-selling products, highest sold products by rating and price, and sales made in different time periods.


## Administrator Functionalities

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



## Customer Functionalities

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


## Run Locally


Go to the Project Directory

```bas
Open the E-Commerce Folder with S.T.S
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

Go to **com.masai package > ECommerceApplication.java**

```bash
Run as Spring Boot App !
```
Open the following URL for Swagger-UI 
```bash
http://localhost:8088/swagger-ui/
```



## Important Note

- At present, Swagger does not support cookie-based authorization. The team will look into implementing it once Swagger provides the necessary features to support it.
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


## ER Diagram

![ER_Diagram](https://user-images.githubusercontent.com/105907169/215553881-33438a04-b956-4547-87c8-d45a14365b3e.jpg)


## PostMan Documentation

Check Out the Below Given Link For Documentation with all API Requests, Responses, Headers & Request Body. 

**POSTMAN DOCUMENTATION** : https://documenter.getpostman.com/view/24342917/2s935kQSBZ


## Base Url
```bash
http://localhost:8088
```
## Login Logout API Reference

![image](https://user-images.githubusercontent.com/105907169/216280259-cfc18b64-2684-4488-9426-0ed409f4ed8e.png)


## Admin API Reference

![image](https://user-images.githubusercontent.com/105907169/215348530-2fed42cd-f777-4c40-ba2b-c9e5e8d043ff.png)


## Customer API Reference
![image](https://user-images.githubusercontent.com/105907169/216280704-67a52dfa-a799-47d1-bd34-b15e87b90182.png)


## Address API Reference

![image](https://user-images.githubusercontent.com/105907169/215348507-699558b2-1a1b-4025-916e-7d76bbb5041e.png)




## Category API Reference

![image](https://user-images.githubusercontent.com/105907169/215348610-d384368e-7fc9-4539-87f1-86acff39a8c2.png)


## Product API Reference

![image](https://user-images.githubusercontent.com/105907169/216281712-6c8475a5-17a4-4899-9b43-81c5e5b64e5d.png)





## Cart API Reference

![image](https://user-images.githubusercontent.com/105907169/215348572-a4311467-1a34-4a17-8a6c-b1141504e8f0.png)


## Order API Reference
![image](https://user-images.githubusercontent.com/105907169/216281277-5406b5c9-dde0-4275-8b9d-332a64e72e2f.png)


## Feedback API Reference
![image](https://user-images.githubusercontent.com/105907169/215348713-d8c07c9c-1a50-4d98-a824-beb235bad975.png)


## Review API Reference
![image](https://user-images.githubusercontent.com/105907169/215348691-9ddb1e82-4111-4bbc-a9ba-55d80ee0a445.png)


## Payment API Reference
![image](https://user-images.githubusercontent.com/105907169/215348759-b2ce4903-ff57-454d-85d6-8d3db0173746.png)


## Contributions

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **Greatly Appreciated**.

If you have any ideas on how to improve this web service, please feel free to fork the repository and submit a pull request. Your contributions, no matter how big or small, are greatly appreciated and will help to make this repository even better.

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
 
