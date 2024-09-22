package com.dipner.SMS.DTO;

public class CollectFeeDTO {
    private String studentName;
    private String currentClass;
    private String phoneNumber;
    private double amountPaying;
    private String paymentMode;

    // Getters and Setters


    public CollectFeeDTO(String studentName, String currentClass, String phoneNumber, double amountPaying, String paymentMode) {
        this.studentName = studentName;
        this.currentClass = currentClass;
        this.phoneNumber = phoneNumber;
        this.amountPaying = amountPaying;
        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAmountPaying() {
        return amountPaying;
    }

    public void setAmountPaying(double amountPaying) {
        this.amountPaying = amountPaying;
    }
}
