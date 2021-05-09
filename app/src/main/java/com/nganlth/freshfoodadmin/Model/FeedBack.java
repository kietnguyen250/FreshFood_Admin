package com.nganlth.freshfoodadmin.Model;

import java.util.ArrayList;

public class FeedBack {
    private String Email_Buyer;
    private String Content_fb;
    private String DateTime;
    private ArrayList<Reply> list_rep;

    public FeedBack() {
    }

    public FeedBack(String email_Buyer, String content_fb, String dateTime) {
        Email_Buyer = email_Buyer;
        Content_fb = content_fb;
        DateTime = dateTime;
    }

    public FeedBack(String email_Buyer, String content_fb, String dateTime, ArrayList<Reply> list_rep) {
        Email_Buyer = email_Buyer;
        Content_fb = content_fb;
        DateTime = dateTime;
        this.list_rep = list_rep;
    }

    public String getEmail_Buyer() {
        return Email_Buyer;
    }

    public void setEmail_Buyer(String email_Buyer) {
        Email_Buyer = email_Buyer;
    }

    public String getContent_fb() {
        return Content_fb;
    }

    public void setContent_fb(String content_fb) {
        this.Content_fb = content_fb;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public ArrayList<Reply> getList_rep() {
        return list_rep;
    }

    public void setList_rep(ArrayList<Reply> list_rep) {
        this.list_rep = list_rep;
    }
}
