package com.example.ema.viewmodels;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Date;


public class ReimbursementViewModel {
    private static ReimbursementViewModel reimbursment;
    private Long id;
    private Date date;
    private String student;
    private double amount;
    private String status;
    private Bitmap imageBitmap;
    private ArrayList<ItemViewModel>items;

    private ReimbursementViewModel(){}

    public static ReimbursementViewModel getInstance()
    {
        if (reimbursment == null)
        {
            reimbursment = new ReimbursementViewModel();
        }
        return reimbursment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public ArrayList<ItemViewModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemViewModel> items) {
        this.items = items;
    }


}
