package com.jdbc.model;

public class Customer {

    private int customerID;
    private String customerName;
    private String location;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
