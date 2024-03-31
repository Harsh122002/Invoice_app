package com.example.bill;

public class Payment {

    private long id;
    private String name;
    private String date; // Changed to lowercase "date"
    private String option;
    private String amount;
    private String number; // Added attribute

    // Constructors
    public Payment() {
    }

    public Payment(long id, String name, String date, String option, String amount, String number) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.option = option;
        this.amount = amount;
        this.number = number; // Set the number attribute
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getOption() {
        return option;
    }

    public String getAmount() {
        return amount;
    }

    public String getNumber() {
        return number;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
