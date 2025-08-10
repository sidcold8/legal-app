package com.btl.model;

import java.time.LocalDate; // Import LocalDate

public class Client {
    private String fullname;
    private String email;
    private String date; // Changed to String to store LocalDate as String
    private String description; // Changed to 'description' from 'dec'

    // Constructor (optional, but good practice)
    public Client() {
    }

    // Getters and Setters
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Changed method name to 'setDate' and parameter type to String
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Changed method name to 'setDescription' and field name
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // You might want to add other fields you use in the form, e.g.:
    // private String phoneNumber;
    // private String timeSlot;
    // private String caseType;

    // public String getPhoneNumber() { return phoneNumber; }
    // public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    // public String getTimeSlot() { return timeSlot; }
    // public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    // public String getCaseType() { return caseType; }
    // public void setCaseType(String caseType) { this.caseType = caseType; }
}