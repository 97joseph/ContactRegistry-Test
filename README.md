# ContactRegistry-Test
 Zurion Technologies Test

 # Contact Registry Application

**A system for storing and managing contact details with REST and SOAP APIs.**
---

## 📌 Features

### **Contact Registry Web App**
- Store and manage contact details (Full Name, Phone Number, Email, etc.).
- Mask and hash phone numbers for security.
- Provide REST API endpoints for CRUD operations.
- Implement a SOAP API for organization-based contact search.
---

## 🛠️ Technologies Used
- **Java Servlets & JSP** (No frameworks required)
- **Maven** - Dependency management
- **MySQL** - Database
- **Apache Tomcat** - Web server
- **Git & Bitbucket** - Version control
- **HTML, CSS, JavaScript** - Frontend

---

## 🚀 Installation Guide

### **Prerequisites**
Ensure you have the following installed on your system:
- **Apache NetBeans IDE/Intellij IDE**<br>
- **Apache Tomcat**<br>
- **MySQL**<br>
- **Java 17 and above**<br>
- **Git**<br>

---
### **Setup Steps**
1. **Clone the repository**
git clone https://bitbucket.org/testasedfgy/contactregistry-test/src/main//<br>
cd contactregistry

2. **Database Setup**
Import the provided SQL script (contacts.sql) into MySQL.
Configure Database Connection(URL,password)

3. **Run the Application**
Deploy the project on Apache Tomcat using NetBeans or manually via the terminal:
mvn clean package

---

### 📌 API Endpoints
REST API (Contact Registry)
Method	Endpoint	Description
- **POST	/contacts	Create a new contact**<br>
- **GET	/contacts/{id}	Get contact details by ID**<br>
- **PUT	/contacts/{id}	Update contact details**<br>
- **DELETE	/contacts/{id}	Delete a contact**<br>
- **GET	/contacts/organization/{org}	Get contacts by organization name**<br>

SOAP API
SOAP endpoint for querying contacts by organization.
WSDL available at: /soap/contacts?wsdl

---

### 📝 Author
Joseph Kibira
