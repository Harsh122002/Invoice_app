package com.example.bill;

public class Purchase {
    private long id;
    private String invoiceNumber;
    private String date;
    private String clientName;
    private String address;
    private String product;
    private String quantity;
    private String unit;
    private String price;
    private String gst;
    private String discount;
    private String amount;
    private String totalAmount;

    // Constructors
    public Purchase() {
        // Default constructor
    }

    public Purchase(long id, String invoiceNumber, String date, String clientName, String address, String product, String quantity, String unit, String price, String gst, String discount, String amount, String totalAmount) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
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

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
