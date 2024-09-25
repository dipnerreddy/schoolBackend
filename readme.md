
# Billing Software for Schools

This project is a comprehensive billing software designed specifically for Radiant High School. It enables the school management to efficiently add and manage student, parent, and staff information in a centralized database. The software also streamlines the process of collecting fees from students.

The backend of the system is powered by Java Spring Boot, ensuring robust and secure handling of data, while the frontend is a modern, responsive web application built using React and styled with Bootstrap for a smooth user experience. PostgreSQL serves as the database for reliable and scalable data storage. Additionally, SonarQube is integrated into the development pipeline to maintain high code quality through continuous testing and analysis.

This solution is tailored to meet the administrative and billing needs of Radiant High School, providing a user-friendly and efficient way to manage their financial operations.


## 🚀 About Me
I'm Dipner Reddy Avuthu, a Full Stack Developer focused on transforming ideas into functional digital products. With expertise in AWS, Spring Boot, React.js, PowerBI, and PostgreSQL, I deliver high-quality web development solutions. Collaboration is key to my work, ensuring smooth communication and customized project execution.

My experience spans freelance web development, AI/ML research, and software development, including projects like the RHS School website. I'm committed to continuous improvement and client satisfaction.

For more details, visit [My Portfolio.](https://dipnerreddy.in/)



## Project Links

- **Backend Code**: [schoolBackend](https://github.com/dipnerreddy/schoolBackend)
- **Frontend Code**: [schoolSoftwareFrontend](https://github.com/dipnerreddy/schoolSoftwareFrontend)
- **Portfolio**: [Dipner Reddy's Portfolio](https://dipnerreddy.in/)
## FAQ

#### How To Run This Project?

1. **Backend**:
   - Clone the [schoolBackend](https://github.com/dipnerreddy/schoolBackend) repository.
   - Navigate to the project directory.
   - Use an IDE like IntelliJ or Eclipse and run the Spring Boot application, or use Maven commands.

2. **Frontend**:
   - Clone the [schoolSoftwareFrontend](https://github.com/dipnerreddy/schoolSoftwareFrontend) repository.
   - Navigate to the project directory.
   - Run `npm install` to install dependencies.
   - Run `npm start` to launch the application.

#### How To Provide Contributions for This Project?

1. Fork the repository.
2. Create a new feature branch.
3. Make your changes.
4. Submit a pull request.

Contributions are welcome!
# API Reference

### Base URL ` http:localhost:8080/admin `

### 1. Login
**POST** `/login`

- **Request Body:**
    ```json
    {
        "username": "string",
        "password": "string"
    }
    ```
- **Response:**
    - `200 OK`: Login successful.
    - `401 Unauthorized`: Invalid username or password.

---

### 2. Add Student with Parent
**POST** `/addStudent`

- **Request Body:**
    ```json
    {
        "parentName": "string",
        "parentPhoneNumber": "string",
        "parentAddress": "string",
        "studentName": "string",
        "dob": "yyyy-MM-dd",
        "admissionYear": "string",
        "currentClass": "string",
        "stillStudying": true,
        "gender": "string",
        "comesByBus": true,
        "busNumber": "string"
    }
    ```
- **Response:**
    - `200 OK`: Student and Parent added/linked successfully.
    - `400 Bad Request`: Class Not Found, Bus Not Found.

---

### 3. Create Class
**POST** `/createClass`

- **Request Body:**
    ```json
    {
        "className": "string",
        "fees": 0,
        "totalStrength": 0,
        "totalPendingFee": 0
    }
    ```
- **Response:**
    - `200 OK`: Class Created Successfully.
    - `400 Bad Request`: Class Already Exists.

---

### 4. Check Balance Fee for a Student
**POST** `/checkBalanceFee`

- **Request Body:**
    ```json
    {
        "phoneNumber": "string",
        "studentName": "string",
        "currentClass": "string"
    }
    ```
- **Response:**
    - `200 OK`: Remaining balance in JSON format.
    - `400 Bad Request`: Parent Not Found, Student Not Found.

---

### 5. Collect Fee
**POST** `/collectFee`

- **Request Body:**
    ```json
    {
        "phoneNumber": "string",
        "studentName": "string",
        "currentClass": "string",
        "amountPaying": 0,
        "paymentMode": "string" // e.g., "UPI", "Cash", "Card"
    }
    ```
- **Response:**
    - `200 OK`: Updated remaining balance in JSON format.
    - `400 Bad Request`: Parent Not Found, Student Not Found, Amount exceeds the remaining balance.

---

### 6. Send WhatsApp Message
**POST** `/sendWhatsAppMessage`

- **Request Body:**
    ```json
    {
        "phoneNumber": "string",
        "message": "string"
    }
    ```
- **Response:**
    - `200 OK`: Message sent successfully.
    - `500 Internal Server Error`: Failed to send message.

---

### 7. Get Students by Class
**GET** `/studentsByClass`

- **Query Parameters:**
    - `className`: string (the name of the class)

- **Response:**
    - `200 OK`: List of students in JSON format.
    - `400 Bad Request`: Class Not Found.

---

### 8. Get Class Balances
**GET** `/classBalances`

- **Response:**
    - `200 OK`: List of class balances in JSON format.

---

### 9. Update Student Status
**POST** `/updateStudentStatus`

- **Request Body:**
    ```json
    {
        "phoneNumber": "string",
        "studentName": "string",
        "stillStudying": true
    }
    ```
- **Response:**
    - `200 OK`: Student status updated successfully.
    - `400 Bad Request`: Parent Not Found, Student Not Found.

---

### 10. Get All Parents
**GET** `/parents`

- **Response:**
    - `200 OK`: List of all parents in JSON format.

---

### 11. Get All Students
**GET** `/students`

- **Response:**
    - `200 OK`: List of all students in JSON format.

---

### 12. Add Bus Details
**POST** `/addBusDetails`

- **Request Body:**
    ```json
    {
        "busNumber": "string",
        "busFee": 0,
        "totalPendingFee": 0,
        "totalStudents": 0
    }
    ```
- **Response:**
    - `200 OK`: Bus details added successfully.

---

### 13. Check Bus Fee
**POST** `/checkBusFee`

- **Request Body:**
    ```json
    {
        "studentName": "string",
        "mobileNumber": "string"
    }
    ```
- **Response:**
    - `200 OK`: Remaining bus fee in JSON format.
    - `400 Bad Request`: Bus Student Not Found.

---

### 14. Collect Bus Fee
**POST** `/collectBusFee`

- **Request Body:**
    ```json
    {
        "studentName": "string",
        "mobileNumber": "string",
        "amountPaying": 0,
        "paymentMode": "string"
    }
    ```
- **Response:**
    - `200 OK`: Payment was successful with updated balance.
    - `400 Bad Request`: Bus Student Not Found, Amount exceeds the remaining balance.

---

### 15. Get Bus Balances
**GET** `/busBalances`

- **Response:**
    - `200 OK`: List of bus balances in JSON format.

---

### 16. Get Students by Bus
**GET** `/studentsByBus`

- **Query Parameters:**
    - `busNumber`: string (the bus number)

- **Response:**
    - `200 OK`: List of students using the bus in JSON format.
    - `400 Bad Request`: Bus Not Found.


## Authors

- [@dipnerreddy](https://www.github.com/dipnerreddy)


## Badges

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)

## Description

This project is a comprehensive billing software designed specifically for Radiant High School. It enables the school management to efficiently add and manage student, parent, and staff information in a centralized database. The software also streamlines the process of collecting fees from students.

The backend of the system is powered by Java Spring Boot, ensuring robust and secure handling of data, while the frontend is a modern, responsive web application built using React and styled with Bootstrap for a smooth user experience. PostgreSQL serves as the database for reliable and scalable data storage. Additionally, SonarQube is integrated into the development pipeline to maintain high code quality through continuous testing and analysis.

This solution is tailored to meet the administrative and billing needs of Radiant High School, providing a user-friendly and efficient way to manage their financial operations.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Deployment

### Frontend

To deploy the frontend of the project, navigate to the frontend directory and run:

```bash
cd frontend
npm install      # Install the required dependencies
npm run build    # Build the project for production
```

### Backend
 Build the application (if using Maven):
```
mvn clean package  # Package the application 
```
Run the application:
``` 
java -jar target/your-backend-app.jar
```

## Used By

This project is used by the following School:

- Radiant High School


## Support

For support, please reach out via email at [contact@dipnerreddy.in](mailto:contact@dipnerreddy.in). 

### Additional Support Options:
- **Documentation**: Comprehensive documentation is available in the `/docs` folder of this repository. This includes setup instructions, API references, and usage guides.
- **Community**: Join our community forum or Discord server for discussions, troubleshooting, and sharing tips with other users.
- **Issue Tracking**: If you encounter any bugs or issues, please report them on the [Issues page](link-to-your-repository/issues) of this repository. Provide as much detail as possible, including steps to reproduce the issue.
- **Feature Requests**: If you have suggestions for new features or enhancements, feel free to submit a request on the [Issues page](link-to-your-repository/issues).
- **Social Media**: Follow us on social media for updates, tips, and community interactions. Links to our profiles can be found in the README.

### Response Time
We strive to respond to all inquiries within 48 hours. Thank you for your patience!

### Contribution
If you're interested in contributing to the project, please read our [Contributing Guidelines](link-to-your-contributing-guidelines) for more information on how to get involved.
