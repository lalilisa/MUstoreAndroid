package com.example.mustore.retrofit.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UpdateUser {
    private String address;
    private String name;


    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dob;
    private String email;
    private String phonenumber;

    public UpdateUser() {
    }

    public UpdateUser(String address, String name, Date dob, String email, String phonenumber) {
        this.address = address;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "UpdateUser{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
