package com.example.mustore.retrofit.response;

import java.io.Serializable;

public class SendOtpView implements Serializable {

    private String transactionId;
    private String otp;

    public SendOtpView(String transactionId, String otp) {
        this.transactionId = transactionId;
        this.otp = otp;
    }

    public SendOtpView() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "SendOtpView{" +
                "transactionId='" + transactionId + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
