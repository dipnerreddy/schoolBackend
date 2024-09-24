package com.dipner.SMS.Logs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CollectBusFeeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String mobileNumber;
    private double amountPaid;
    private double totalBalance;
    private String dateTime; // You can set this when creating the log
    private String paymentMode; // Include payment mode

    // Constructor
    public CollectBusFeeLog(String studentName, String mobileNumber, double amountPaid, double totalBalance, String paymentMode, String dateTime) {
        this.studentName = studentName;
        this.mobileNumber = mobileNumber;
        this.amountPaid = amountPaid;
        this.totalBalance = totalBalance;
        this.paymentMode = paymentMode;
        this.dateTime = dateTime;
    }

    // Default constructor
    public CollectBusFeeLog() {}

    // Getters and setters
    // ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
