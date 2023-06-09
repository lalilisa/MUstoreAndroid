package com.example.mustore.retrofit.request;

public class ChangePassword {
    private String oldPass;
    private String newPass;

    public ChangePassword() {
    }

    public ChangePassword(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "oldPass='" + oldPass + '\'' +
                ", newPass='" + newPass + '\'' +
                '}';
    }
}