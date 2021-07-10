package com.example.finalproject;

public class User {
    public String id;
    public String email;
    public String password;

    public String schoolClass;
    public String schoolNumber;


    public User() {
    }

    public User(String email, String password, String id, String schoolClass, String schoolNumber) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.schoolClass = schoolClass;
        this.schoolNumber = schoolNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", schoolClass='" + schoolClass + '\'' +
                ", schoolNumber='" + schoolNumber + '\'' +
                '}';
    }
}
