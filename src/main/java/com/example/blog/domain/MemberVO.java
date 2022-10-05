package com.example.blog.domain;

import com.example.blog.crypto.Crypto;

import java.security.GeneralSecurityException;
import java.sql.Date;

public class MemberVO {
    private String id;
    private String password;
    private String name;
    private String phone;
    private String department;
    private Date sign_up_date;
    private Crypto crypto;

    public MemberVO() {
        super();
    }

    public MemberVO(String id, String password, String name, String phone, String department, Date sign_up_date) throws GeneralSecurityException {
        crypto = new Crypto();
        this.id = id;
        this.password = crypto.encrypt(password);
        this.name = crypto.encrypt(name);
        this.phone = crypto.encrypt(phone);
        this.department = crypto.encrypt(department);
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

    public void setPassword(String password) throws GeneralSecurityException {
        this.password = crypto.encrypt(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws GeneralSecurityException {
        this.name = crypto.encrypt(name);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws GeneralSecurityException {
        this.phone = crypto.encrypt(phone);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) throws GeneralSecurityException {
        this.department = crypto.encrypt(department);
    }

    public Date getSign_up_date() {
        return sign_up_date;
    }

    public void setSign_up_date(Date sign_up_date) {
        this.sign_up_date = sign_up_date;
    }
}
