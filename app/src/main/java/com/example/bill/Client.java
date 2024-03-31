package com.example.bill;

public class Client {
    private long id;
    private String name;
    private String role;
    private String gstNumber;
    private String mobileNumber;
    private String email;
    private String address;

    // Constructor
    public Client() {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gstNumber = gstNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.address = address;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
