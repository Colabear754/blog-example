package com.example.blog.domain;

import java.sql.Date;

public class MemberVO {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String department;
    private Date sign_up_date;

    public MemberVO() {
        super();
    }

    public MemberVO(String id, String password, String name, String phone, String department, Date sign_up_date) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.department = department;
        this.sign_up_date = sign_up_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getSign_up_date() {
        return sign_up_date;
    }

    public void setSign_up_date(Date sign_up_date) {
        this.sign_up_date = sign_up_date;
    }
}
