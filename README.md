# Coding Exercise

---

## API Endpoints

### 1. Get Transactions by Category
- **Endpoint**: `GET /transactions/category/{category}`
- **Description**: Retrieves all transactions for a given category, sorted by date (latest first).
- **Example Request**:
  ```bash
  curl -u user:password http://localhost:8080/transactions/category/Groceries
  ```

### 2. Get Total Outgoing per Category
- **Endpoint**: `GET /transactions/total/{category}`
- **Description**: Calculates the total outgoing amount for a given category.
- **Example Request**:
  ```bash
  curl -u user:password http://localhost:8080/transactions/total/MyMonthlyDD
  ```

### 3. Get Monthly Average Spend
- **Endpoint**: `GET /transactions/average/{category}`
- **Description**: Calculates the monthly average spend for a given category.
- **Example Request**:
  ```bash
  curl -u user:password http://localhost:8080/transactions/average/Groceries
  ```

### 4. Get Highest Spend in a Category for a Given Year
- **Endpoint**: `GET /transactions/highest/{category}/{year}`
- **Description**: Retrieves the highest spend in a given category for a specific year.
- **Example Request**:
  ```bash
  curl -u user:password http://localhost:8080/transactions/highest/Groceries/2020
  ```

### 5. Get Lowest Spend in a Category for a Given Year
- **Endpoint**: `GET /transactions/lowest/{category}/{year}`
- **Description**: Retrieves the lowest spend in a given category for a specific year.
- **Example Request**:
  ```bash
  curl -u user:password http://localhost:8080/transactions/lowest/Groceries/2020
  ```
  
## Basic Authentication
- **Username**: `user`
- **Password**: `password`
- Use these credentials to access the API endpoints. For example:
  ```bash
  curl -u user:password http://localhost:8080/transactions/category/Groceries
  ```

## Error Handling
- If a category does not exist, the API returns a 404 Not Found response with the following format:
  ```json
  {
    "message": "Category not found: NonExistentCategory",
    "status": 404
  }
  ```

## Running Tests
- To run the unit tests, use the following command:
  ```bash
  mvn test
  ```