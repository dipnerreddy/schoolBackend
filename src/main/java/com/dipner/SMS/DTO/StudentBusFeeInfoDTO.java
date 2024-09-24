package com.dipner.SMS.DTO;

public class StudentBusFeeInfoDTO {
    private String studentName;
    private String mobileNumber;
    private double remainingBalance;

    // Constructor
    public StudentBusFeeInfoDTO(String studentName, String mobileNumber, double remainingBalance) {
        this.studentName = studentName;
        this.mobileNumber = mobileNumber;
        this.remainingBalance = remainingBalance;
    }

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

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
