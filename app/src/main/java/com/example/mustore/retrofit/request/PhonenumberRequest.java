package com.example.mustore.retrofit.request;

import java.io.Serializable;

public class PhonenumberRequest implements Serializable {
    private String phonenumber;

    public PhonenumberRequest() {
    }

    public PhonenumberRequest(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "PhonenumberRequest{" +
                "phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
