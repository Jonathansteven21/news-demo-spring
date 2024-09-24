# News Management Platform

This project is a **News Management Platform** built using **Spring Boot**. It allows users to manage news articles, register accounts, upload profile images, and interact with an admin dashboard. The system supports different roles, including **users** and **admins**, and uses **Spring Security** for authentication and authorization.

## Features

- **User Registration & Login**: Users can create an account, log in, and manage their profiles.
- **Admin Dashboard**: Admin users can manage news articles and authors from a separate dashboard.
- **News Management**: Create, update, search, and delete news articles associated with authors.
- **Author Management**: Manage the list of authors (create, update, and search).
- **Profile Image Handling**: Upload profile images that are displayed on the user’s profile.
- **Error Handling**: Custom error pages for HTTP errors (e.g., 404, 403).
  
## Technologies Used

- **Java**
- **Spring Boot**
- **Spring Security**
- **Thymeleaf**
- **MySQL / H2 Database** (or your preferred database)
- **BCrypt** for password encryption
- **Maven**

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven**
- **MySQL** (or use **H2** for testing purposes)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/Jonathansteven21/news-demo-spring.git
    ```

2. Navigate to the project directory:
    ```bash
    cd news-demo-spring
    ```

3. Configure the database in `src/main/resources/application.properties`. Use **MySQL** or **H2** as per your preference:
    ```properties
    # For MySQL
    spring.datasource.url=jdbc:mysql://localhost:3306/news
    spring.datasource.username=root
    spring.datasource.password=yourpassword

    # For H2
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=password
    spring.h2.console.enabled=true
    ```

4. Run the application using **Maven**:
    ```bash
    mvn spring-boot:run
    ```

### Default User Accounts

- **Admin Account**:  
  Email: `admin@admin.com`  
  Password: `Pass!12345`

- **User Account**:  
  Email: `user@user.com`  
  Password: `Pass!12345`

### Usage

1. Navigate to `http://localhost:8080` to access the homepage.
2. Use the **Admin account** to manage news articles and authors.
3. **User accounts** can log in, view articles, and manage their profiles.

### Key Endpoints

- **Home Page**: `http://localhost:8080/`
- **Login Page**: `http://localhost:8080/login`
- **News Management**:
  - List news: `http://localhost:8080/news/list`
  - Create news: `http://localhost:8080/news/register`
- **Author Management**:
  - List authors: `http://localhost:8080/author/list`
  - Create author: `http://localhost:8080/author/register`
- **Admin Dashboard**: `http://localhost:8080/admin/dashboard`

## Security

This application uses **Spring Security** to enforce access control:
- **Admins** have access to `/admin/*` routes.
- **Users** can log in and access general news pages.
- Passwords are encrypted using **BCrypt**.

## Project Structure

```bash
src/
├── main/
│   ├── java/com/egg/news/
│   │   ├── controllers/       # Controllers handling web requests
│   │   ├── entities/          # JPA entities (User, News, Author)
│   │   ├── repositories/      # Data access layer (JPA repositories)
│   │   ├── services/          # Business logic layer (Services)
│   │   └── utils/             # Utility classes (Validation, etc.)
│   ├── resources/
│   │   ├── static/            # Static assets (CSS, JS, images)
│   │   └── templates/         # Thymeleaf templates for views
│   └── application.properties # Configuration file
├── test/
│   └── java/com/egg/news/      # Unit and integration tests
