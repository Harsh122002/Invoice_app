package com.example.bill;

public class Item {
    private String name;
    private double quantity;
    private double price;
    private double gst;
    private double discount;

    public Item(String name, double quantity, double price, double gst, double discount) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.gst = gst;
        this.discount = discount;
    }

    // Add getters and setters as needed
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
