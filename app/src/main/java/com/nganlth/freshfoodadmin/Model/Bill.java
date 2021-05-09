package com.nganlth.freshfoodadmin.Model;

public class Bill {
    private String Bill_id;
    private String date;
    private String email_buyer;
    private String address_buyer;


    public Bill() {
    }

    public Bill(String bill_id, String date, String email_buyer, String address_buyer) {
        Bill_id = bill_id;
        this.date = date;
        this.email_buyer = email_buyer;
        this.address_buyer = address_buyer;
    }

    public String getBill_id() {
        return Bill_id;
    }

    public void setBill_id(String bill_id) {
        Bill_id = bill_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail_buyer() {
        return email_buyer;
    }

    public void setEmail_buyer(String email_buyer) {
        this.email_buyer = email_buyer;
    }

    public String getAddress_buyer() {
        return address_buyer;
    }

    public void setAddress_buyer(String address_buyer) {
        this.address_buyer = address_buyer;
    }
}