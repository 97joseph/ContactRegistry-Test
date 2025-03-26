// GET Contacts by Organization
function fetchContacts() {
    let org = document.getElementById("organization").value;

    if (!org) {
        alert("Please enter an organization name.");
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:8080/ContactRegistry_war/contacts?organization=${org}`, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let contacts = JSON.parse(xhr.responseText);
            displayContacts(contacts);
        }
    };
    xhr.send();
}

// POST (Add New Contact)
function addContact() {
    let fullName = document.getElementById("fullName").value;
    let phoneNumber = document.getElementById("phoneNumber").value;
    let email = document.getElementById("email").value;
    let idNumber = document.getElementById("idNumber").value;
    let dob = document.getElementById("dob").value;
    let gender = document.getElementById("gender").value;
    let organization = document.getElementById("organizationName").value;

    if (!fullName || !phoneNumber || !idNumber || !dob || !gender) {
        alert("Full Name, Phone Number, ID Number, Date of Birth, and Gender are required.");
        return;
    }

    let contactData = JSON.stringify({
        fullName: fullName,
        phoneNumber: phoneNumber,
        email: email,
        idNumber: idNumber,
        dateOfBirth: dob,
        gender: gender,
        organization: organization
    });

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/ContactRegistry_war/contacts", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 201) {
                alert("Contact added successfully!");
                fetchContacts();
            } else {
                alert("Error adding contact.");
            }
        }
    };
    xhr.send(contactData);
}

// DELETE Contact
function deleteContact(contactId) {
    if (!confirm("Are you sure you want to delete this contact?")) return;

    let xhr = new XMLHttpRequest();
    xhr.open("DELETE", `http://localhost:8080/ContactRegistry_war/contacts?id=${contactId}`, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert("Contact deleted successfully!");
                fetchContacts();
            } else {
                alert("Error deleting contact.");
            }
        }
    };
    xhr.send();
}

// Display Contacts in Table
function displayContacts(contacts) {
    let table = document.getElementById("contactTable");
    table.innerHTML = "";

    contacts.forEach(contact => {
        let row = document.createElement("tr");

        row.innerHTML = `
            <td>${contact.id}</td>
            <td>${contact.fullName}</td>
            <td>${contact.phoneNumber}</td>
            <td>${contact.email || "N/A"}</td>
            <td>${contact.idNumber}</td>
            <td>${contact.dateOfBirth}</td>
            <td>${contact.gender}</td>
            <td>${contact.organization || "N/A"}</td>
            <td><button onclick="deleteContact(${contact.id})">Delete</button></td>
        `;

        table.appendChild(row);
    });
}