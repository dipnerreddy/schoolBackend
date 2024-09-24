package com.dipner.SMS.DTO;

public class CollectBusFeeDTO {
    private String studentName;
    private String mobileNumber;
    private double amountPaying;
    private String paymentMode; // Add this field for payment method

    // Getters and Setters
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

    public double getAmountPaying() {
        return amountPaying;
    }

    public void setAmountPaying(double amountPaying) {
        this.amountPaying = amountPaying;
    }

    public String getPaymentMode() {
        return paymentMode; // Getter for paymentMode
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode; // Setter for paymentMode
    }
}
