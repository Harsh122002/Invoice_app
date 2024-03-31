package com.example.bill;

public class Items {

    private long id;
    private String name;
    private String price;
    private String discount;
    private String gst;

    // Constructors
    public Items() {
    }

    public Items(long id, String name, String price, String discount, String gst) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.gst = gst;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getGst() {
        return gst;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
