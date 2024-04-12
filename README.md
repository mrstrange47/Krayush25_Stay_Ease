**STAY-EASE**

**PostMan Collection**
[Stay Ease.postman_collection.json](https://github.com/mrstrange47/Krayush25_Stay_Ease/files/14963509/Stay.Ease.postman_collection.json)


**Problem Statement**

Develop and deploy a RESTful API service using Spring Boot to streamline the room booking process for a hotel management aggregator application. 

You are required to use MySQL to persist the data.

**Key Features**

Any hotel manager can update any hotel details i.e you do not have to keep track of who manages which hotel

Another service handles check-in and check-out functionalities

The service must implement authentication and authorization

The service uses JWT tokens for session management

The service must have three roles: CUSTOMER, HOTEL MANAGER, and ADMIN

The service must have two types of API endpoints:

Public endpoints - Anyone can access (Ex. Registration, Login)

Private endpoints - Only authenticated users can access (Ex. Book a room)

**The API must have the following features:**

**User Registration and Login**

Users must be able to register by providing their email address and, password

The password must be encrypted and stored using BCrypt

Fields: Email, Password, First Name, Last Name, Role

The Role must be defaulted to “Customer” if it is not specified

A JWT token must be generated upon successful registration or login

**Hotel Management**

Store and manage hotel details

Fields: Hotel Name, Location, Description, Number of Available Rooms

The number of available rooms indicates whether a booking can be made or not

Anyone can browse all the available hotels (Public endpoint)

Only the administrator is allowed to create and delete hotels

The hotel manager can only update the hotel details

**Booking Management**

Customers must be able to book rooms using the service

A single room can be booked per request

Only hotel managers are allowed to cancel the booking
