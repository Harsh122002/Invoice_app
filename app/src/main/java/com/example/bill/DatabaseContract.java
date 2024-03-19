package com.example.bill;

public class DatabaseContract {
    public static class UserEntry {
        public static final String USER_TABLE_NAME = "users";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_SHOP_NAME = "shop_name";
        public static final String COLUMN_GST_NO = "gst_number";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PIN_CODE = "pincode";
    }


    private String shopName;
    private String phone;
    private String email;
    private String password;
    private String address;
    private String pinCode;
    private String gstNumber;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }
}
