package com.example.mustore.retrofit.response;

import com.example.mustore.common.Category;

import java.io.Serializable;
import java.util.Date;

public class UserView implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phonnumber;
    private String password;
    private Date dob;
    private String address;
    private String fullname;
    private Integer gender;
    private Category.Role role;
    private Integer isNotifi;

    public UserView() {
    }

    public UserView(Long id, String username, String email, String phonnumber, String password, Date dob, String address, String fullname, Integer gender, Category.Role role, Integer isNotifi) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phonnumber = phonnumber;
        this.password = password;
        this.dob = dob;
        this.address = address;
        this.fullname = fullname;
        this.gender = gender;
        this.role = role;
        this.isNotifi = isNotifi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonnumber() {
        return phonnumber;
    }

    public void setPhonnumber(String phonnumber) {
        this.phonnumber = phonnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Category.Role getRole() {
        return role;
    }

    public void setRole(Category.Role role) {
        this.role = role;
    }

    public Integer getIsNotifi() {
        return isNotifi;
    }

    public void setIsNotifi(Integer isNotifi) {
        this.isNotifi = isNotifi;
    }

    @Override
    public String toString() {
        return "UserView{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phonnumber='" + phonnumber + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                ", isNotifi=" + isNotifi +
                '}';
    }
}
