# Blog API Project Overview

I developed a **Blog API** using **Spring Boot**, **Spring JPA**, **Lombok**, and **Spring Security** to provide a robust platform for managing blog content. The application includes user authentication and authorization using **JWT (JSON Web Tokens)** and implements CRUD operations on both blogs and comments, along with additional functionalities like publishing, archiving, liking, and disliking posts and comments. All features are integrated with **pagination** to manage data efficiently, and the database used is **PostgreSQL**.


### **Brief Overview:**
- **User Authentication:** Implemented secure authentication using JSON Web Tokens (JWT).
- **Blog Management:** Users can create, read, update, and delete blog posts. Blogs can be published, archived, or retrieved based on status.
- **Comment System:** Users can add, edit, delete, and view comments on blog posts.
- **Interactions:** Users can like or dislike blogs and comments.
- **Pagination:** Implemented pagination for efficiently handling large sets of blogs and comments.
- **Database:** PostgreSQL is used as the database for storing all the data.


### Key Features

1. **User Authentication and Authorization**:
   - Users can register and authenticate using JWT, which secures the API endpoints.
   - Example API Paths:
     - **Register User**: `POST http://localhost:8080/api/v1/auth/register`
     - **Authenticate User**: `POST http://localhost:8080/api/v1/auth/authenticate`

2. **Blog Management**:
   - **Create, Read, Update, and Delete (CRUD)** operations are provided for blogs.
   - Additional functionalities include:
     - **Publish or Archive Blogs**: Blogs can be published or archived based on user actions.
     - **Pagination**: All blog lists are paginated for better performance and user experience.
   - Example API Paths:
     - **Create Blog**: `POST http://localhost:8080/api/v1/blogs`
     - **Get All Blogs**: `GET http://localhost:8080/api/v1/blogs`
     - **Get Specific Blog by ID**: `GET http://localhost:8080/api/v1/blogs/{blog-id}`
     - **Get Blogs by Owner**: `GET http://localhost:8080/api/v1/blogs/owner`
     - **Get Archived Blogs**: `GET http://localhost:8080/api/v1/blogs/archived`
     - **Update Blog**: `PUT http://localhost:8080/api/v1/blogs/{blog-id}`
     - **Delete Blog**: `DELETE http://localhost:8080/api/v1/blogs/{blog-id}`

3. **Comment Management**:
   - Users can add, view, and delete comments on blog posts.
   - Additional functionalities include:
     - **Liking/Disliking Comments**: Users can express their opinions on comments.
     - **Pagination**: Comment lists are paginated for easier navigation and management.
   - Example API Paths:
     - **Create Comment**: `POST http://localhost:8080/api/v1/blogs/{blog-id}/comments`
     - **Get All Comments**: `GET http://localhost:8080/api/v1/blogs/{blog-id}/comments`

4. **Like/Dislike Feature**:
   - Users can like or dislike blogs and comments, providing interactive feedback mechanisms.
   - Example API Paths:
     - **Like Blog**: `POST http://localhost:8080/api/v1/blogs/{blog-id}/like`
     - **Dislike Blog**: `DELETE http://localhost:8080/api/v1/blogs/{blog-id}/dislike`
     - **Like Comment**: `POST http://localhost:8080/api/v1/blogs/{blog-id}/comments/{comment-id}/like`
     - **Dislike Comment**: `DELETE http://localhost:8080/api/v1/blogs/{blog-id}/comments/{comment-id}/dislike`

### Technical Stack

- **Backend Framework**: Spring Boot
- **Data Persistence**: Spring JPA (Java Persistence API)
- **Security**: Spring Security with JWT for authentication and authorization
- **Database**: PostgreSQL
- **Utilities**: Lombok for reducing boilerplate code
- **Pagination**: Implemented across various endpoints to manage large data sets efficiently

This project demonstrates a comprehensive approach to building a secure, scalable, and feature-rich blog platform with a clear focus on user management and content interaction.
