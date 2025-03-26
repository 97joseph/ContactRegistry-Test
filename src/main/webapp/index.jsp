<%--
  Created by IntelliJ IDEA.
  User: Joseph Kibira
  Date: 25/03/2025
  Time: 3:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contact Directory</title>
    <script src="js/soapRequest.js"></script>
</head>
<body>
<h2>Search Contacts by Organization</h2>

<input type="text" id="organization" placeholder="Enter Organization Name">
<button onclick="fetchContacts()">Search</button>

<h3>Results:</h3>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Phone</th>
        <th>Email</th>
        <th>ID Number</th>
        <th>Date of Birth</th>
        <th>Gender</th>
        <th>Organization</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody id="contactTable">
    </tbody>
</table>

<h2>Add New Contact</h2>
<input type="text" id="fullName" placeholder="Full Name">
<input type="text" id="phoneNumber" placeholder="Phone Number">
<input type="email" id="email" placeholder="Email">
<input type="text" id="idNumber" placeholder="ID Number">
<input type="date" id="dob">
<select id="gender">
    <option value="">Select Gender</option>
    <option value="Male">Male</option>
    <option value="Female">Female</option>
    <option value="Other">Other</option>
</select>
<input type="text" id="organizationName" placeholder="Organization">
<button onclick="addContact()">Add Contact</button>

</body>
</html>