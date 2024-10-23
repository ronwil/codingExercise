## README.md

**Project Name:** Spring Boot Package Management API

**Description:**

This project implements a backend RESTful web service for managing packages consisting of one or more products. It allows users to create, retrieve, update, and delete packages, with support for currency conversion.
The default currency is USD if sent in the API.

**Technical Stack:**

- Java
- Spring Boot

**Acceptance Criteria:**

- **API Endpoints:**
    - Create a package (POST)
    - Retrieve a package (GET by ID)
    - Update a package (PUT)
    - Delete a package (DELETE)
    - List all packages (GET)
- **Package Attributes:**
    - ID (generated)
    - Name
    - Description
    - Products (list of product IDs)
    - Price (total of product prices in USD)
- **Currency Conversion:**
    - Specify currency in request
    - Default currency: USD
    - Uses a free API service like Frankfurter.app to get latest currency conversion ([https://frankfurter.app/](https://frankfurter.app/))
- **Product Prices:**
    - Assumes product creation exists but not deletion (external API)
    - Fetches product prices from a separate product service ([https://product-service.herokuapp.com/api/v1/products](https://product-service.herokuapp.com/api/v1/products))

**Getting Started:**

1. **Prerequisites:**
    - Java Development Kit (JDK) 8+
    - Maven (or similar build tool)
2. **Clone Repository:**
    - Clone this repository using Git.
3. **Run the Application:**
    - Build the project: `mvn clean install` (or equivalent command for your build tool)
    - Run the application: `java -jar target/codingexercise-v1.jar` (adjust the JAR filename if different)

**Contributing:**

Feel free to submit pull requests for improvements or bug fixes.
