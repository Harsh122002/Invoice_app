package com.example.bill;

public class Item {
    private String date;
    private String clientName;
    private String address;
    private String product;
    private int quantity;
    private String unit;
    private double price;
    private double gst;
    private double discount;
    private double amount;
    private double totalAmount;

    // Constructor
    public Item(String date, String clientName, String address, String product, int quantity, String unit, double price, double gst, double discount, double amount, double totalAmount) {
        this.date = date;
        this.clientName = clientName;
        this.address = address;
        this.product = product;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.gst = gst;
        this.discount = discount;
        this.amount = amount;
        this.totalAmount = totalAmount;
    }

    public static void add(Items items) {

    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAddress() {
        return address;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public double getGst() {
        return gst;
    }

    public double getDiscount() {
        return discount;
    }

    public double getAmount() {
        return amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
