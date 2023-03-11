package com.shruteekatech.eCommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;


    @Size(min = 5, max = 50)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, max = 14)
    private String phone;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String password;

    @NotEmpty
    @Size(min = 2, max = 200)
    private String address;

    @NotEmpty
    @Size(min = 2, max = 15)
    private String gender;

    @Size(min = 2, max = 100)
    private String about;

    private boolean isActive;

    public UserDto() {
    }

    public UserDto(String name, String email, String phone, String password, String address, String gender, String about, boolean isActive) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.about = about;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", about='" + about + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
