## Overview

The **Course Registration System** is a web-based application built using **Spring Boot**, where students can register for courses based on a **First Come, First Serve (FCFS)** basis. The system handles concurrent requests, maintains data consistency, and provides a RESTful API for seamless integration.

---

## Tech Stack

- **Backend:** Spring Boot 3.4 (Java 21)  
- **Database:** PostgreSQL  
- **Authentication:** JWT & Spring Security  
- **API Documentation:** Swagger UI  
- **Deployment:** Dockerized and hosted on **Render**  
- **Build Tool:** Maven  
- **IDE:** IntelliJ IDEA / Spring Tool Suite  
- **Testing:** Postman  

---

## Features

### User Management
- **JWT Authentication** – Secure login with token-based authentication  
- **Role-Based Access Control** – Separate functionalities for Admins, Students, and Professors  

### Admin Features
- Manage Students, Professors, Semesters, and Courses  
- Automated Email Notifications for Course Registration  
- Assign Courses using FCFS Algorithm  
- Update Semester and Course Details  

### Student Features
- View and Edit Personal Details  
- View Available Courses  
- Submit Course Registration Requests  

### Professor Features
- View and Edit Personal Details  
- View Assigned Courses  

---

## API Documentation

For detailed API documentation, you can access the **Swagger UI** interface hosted on Render:

[Swagger UI Documentation](https://courseregistrationapplication.onrender.com/swagger-ui/index.html#/) (It might take some time to load)

This link provides comprehensive details about all the available APIs, including request/response formats, endpoints, and authentication mechanisms.

---

## System Architecture

Below is a high-level system architecture diagram representing the Course Registration System:

![System Architecture](https://github.com/krish1832/CourseRegistrationApplication/blob/master/src/main/java/com/example/Course/Registration/App/Diagram.png)

---

## Setup and Installation

### Prerequisites
- Install **Java 21**  
- Install **PostgreSQL**  
- Install **PgAdmin**  
- Install **Maven**  
- Install **Docker** (for deployment)  

### Steps to Run Locally
1. Clone the repository:
   ```sh
   git clone https://github.com/krish1832/course-registration-system.git
   cd course-registration-system

