package com.nganlth.freshfoodadmin.Model;

public class Reply {
    private String email_admin;
    private String Text_reply;

    public Reply() {
    }

    public Reply(String email_admin, String text_reply) {
        this.email_admin = email_admin;

        Text_reply = text_reply;
    }

    public String getEmail_admin() {
        return email_admin;
    }

    public void setEmail_admin(String email_admin) {
        this.email_admin = email_admin;
    }

    public String getText_reply() {
        return Text_reply;
    }

    public void setText_reply(String text_reply) {
        Text_reply = text_reply;
    }
}
