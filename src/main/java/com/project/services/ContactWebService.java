package com.project.services;

import com.project.models.Contact;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService(targetNamespace = "http://soap.project.com/")
public class ContactWebService {
    private final ContactService contactService = new ContactService();

    @WebMethod
    public List<Contact> getContactsByOrganization(String organization) {
        return contactService.getContactsByOrganization(organization);
    }
}