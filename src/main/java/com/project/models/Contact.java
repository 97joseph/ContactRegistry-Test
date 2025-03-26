package com.project.models;

public class Contact {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String dateOfBirth;
    private String gender;
    private String organization;
    private String maskedName;
    private String maskedPhone;
    private String phoneHash;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getOrganization() {
        return organization;
    }

    public String getMaskedName() {
        return maskedName;
    }

    public String getMaskedPhone() {
        return maskedPhone;
    }

    public String getPhoneHash() {
        return phoneHash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setMaskedName(String maskedName) {
        this.maskedName = maskedName;
    }

    public void setMaskedPhone(String maskedPhone) {
        this.maskedPhone = maskedPhone;
    }

    public void setPhoneHash(String phoneHash) {
        this.phoneHash = phoneHash;
    }

    public Contact(int id, String fullName, String phoneNumber, String email, String idNumber, String dateOfBirth, String gender, String organization, String maskedName, String maskedPhone, String phoneHash) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
        this.maskedName = maskedName;
        this.maskedPhone = maskedPhone;
        this.phoneHash = phoneHash;
    }
}