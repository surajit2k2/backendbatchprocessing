package com.ibm.batch.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class StudentDTO {
	 
    private String emailAddress;
    private String name;
    private String purchasedPackage;
 
    public StudentDTO() {}
 
    public String getEmailAddress() {
        return emailAddress;
    }
 
    public String getName() {
        return name;
    }
 
    public String getPurchasedPackage() {
        return purchasedPackage;
    }
 
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void setPurchasedPackage(String purchasedPackage) {
        this.purchasedPackage = purchasedPackage;
    }
}
