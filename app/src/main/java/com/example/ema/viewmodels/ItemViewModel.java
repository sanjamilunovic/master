package com.example.ema.viewmodels;

import java.util.Date;

public class ItemViewModel {
    private Date purchaseDate;
    private String invoice;
    private String category;
    private int amount;
    private String educationalBenefit;
    private String description;
    private String type;
    private String vendor;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEducationalBenefit() {
        return educationalBenefit;
    }

    public void setEducationalBenefit(String educationalBenefit) {
        this.educationalBenefit = educationalBenefit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
