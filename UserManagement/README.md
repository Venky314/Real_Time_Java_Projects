# User Management System

A Spring Boot application for user management with registration, login, password reset, and dashboard functionalities.

## Features

1. **User Registration** with dependent dropdowns (Country → State → City)
2. **Email Sending** upon successful registration with temporary password
3. **Password Reset** functionality for first-time login
4. **User Login** authentication
5. **Dashboard** with random quote display

## Technology Stack

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- Spring MVC
- Thymeleaf (Template Engine)
- MySQL Database
- Spring Mail (Gmail SMTP)
- SpringDoc OpenAPI (Swagger)
- Bootstrap 5
- JavaScript
- Lombok
- Maven

## Prerequisites

1. Java 21 or higher
2. Maven 3.6 or higher
3. MySQL 8.0 or higher
4. Gmail account with App Password (for email functionality)

## Setup Instructions

### 1. Database Setup

1. Create a MySQL database named `jrtp`
2. Execute the provided `database-script.sql` file to create tables and insert static data

```sql
mysql -u root -p < database-script.sql
```

### 2. Application Configuration

1. Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jrtp
spring.datasource.username=your-username
spring.datasource.password=your-password
```

2. Configure Gmail SMTP settings:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Note:** For Gmail, you need to:
- Enable 2-factor authentication
- Generate an App Password from Google Account settings

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Frontend Pages

### Web Endpoints
- **Home**: `http://localhost:8080/` (redirects to login)
- **Login**: `http://localhost:8080/login`
- **Registration**: `http://localhost:8080/register`
- **Dashboard**: `http://localhost:8080/dashboard`
- **Reset Password**: `http://localhost:8080/reset-password`
- **Logout**: `http://localhost:8080/logout`

### Features
- **Responsive Design**: Mobile-friendly Bootstrap 5 interface
- **Dependent Dropdowns**: Country → State → City selection
- **Real-time Validation**: Client-side form validation
- **Session Management**: Secure user sessions
- **Error Handling**: User-friendly error messages
- **Modern UI**: Gradient backgrounds, animations, and transitions

## API Endpoints

### Base URL: `http://localhost:8080/api/users`

### 1. Get All Countries
```
GET /api/users/countries
```

### 2. Get States by Country
```
GET /api/users/states/{countryId}
```

### 3. Get Cities by State
```
GET /api/users/cities/{stateId}
```

### 4. User Registration
```
POST /api/users/register
Content-Type: application/json

{
  "userName": "John Doe",
  "email": "john@example.com",
  "phoneNumber": "1234567890",
  "countryId": 1,
  "stateId": 1,
  "cityId": 1
}
```

### 5. User Login
```
POST /api/users/login?email=john@example.com&password=tempPassword
```

### 6. Reset Password
```
POST /api/users/reset-password
Content-Type: application/json

{
  "email": "john@example.com",
  "oldPassword": "tempPassword",
  "newPassword": "newSecurePassword"
}
```

### 7. Get Random Quote (Dashboard)
```
GET /api/users/dashboard/quote
```

## Swagger Documentation

Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

Access OpenAPI docs at: `http://localhost:8080/api-docs`

## Database Schema

### Tables:
1. **country_master** - Country information
2. **states_master** - State information with country reference
3. **cities_master** - City information with state reference
4. **user_master** - User information with location references

## Sample Workflow

### Frontend Workflow
1. **Registration**: 
   - Navigate to `/register`
   - Fill out the form with user details
   - Select Country → State → City from dependent dropdowns
   - Submit form
   - System generates temporary password and sends email
   - Redirect to login page with success message

2. **First Login**:
   - Navigate to `/login`
   - Enter email and temporary password
   - System redirects to password reset page

3. **Password Reset**:
   - Enter email, old password, and new password
   - Submit form
   - Redirect to login page with success message
   - User can now login with new password

4. **Dashboard**:
   - After successful login, user is redirected to `/dashboard`
   - View random quote and profile information
   - Can refresh quote or change password

### API Workflow
1. **Registration**: 
   - Call `/api/users/register` with user details
   - System generates temporary password and sends email
   - User receives email with temporary password

2. **First Login**:
   - Call `/api/users/login` with email and temporary password
   - Response indicates password reset is required

3. **Password Reset**:
   - Call `/api/users/reset-password` with old and new passwords
   - User can now login with new password

4. **Dashboard**:
   - Call `/api/users/dashboard/quote` to get random quote

## Error Handling

All API responses follow this format:

```json
{
  "status": "SUCCESS|ERROR",
  "message": "Description message",
  "data": "Response data (if applicable)"
}
```

## Testing with Postman

Import the following collection examples:

1. **Get Countries**: GET request to `/api/users/countries`
2. **Register User**: POST request to `/api/users/register` with user JSON
3. **Login**: POST request to `/api/users/login` with query parameters
4. **Reset Password**: POST request to `/api/users/reset-password` with reset JSON
5. **Get Quote**: GET request to `/api/users/dashboard/quote`

## Troubleshooting

1. **Database Connection**: Ensure MySQL is running and credentials are correct
2. **Email Issues**: Verify Gmail App Password and SMTP settings
3. **Build Issues**: Check Java version and Maven dependencies
4. **Port Conflicts**: Change server port in `application.properties` if needed

## Notes

- The application uses `ddl-auto=update` for JPA, which automatically updates the schema
- Temporary passwords are 8-character alphanumeric strings
- All passwords are stored in plain text for this demo (use password hashing in production)
- The quote API uses an external service: `https://dummyjson.com/quotes/random`
