package com.dipner.SMS.DTO;

public class StudentFeeInfoDTO {
    private String studentName;
    private String parentPhoneNumber;
    private double remainingBalance;

    public StudentFeeInfoDTO(String studentName, String parentPhoneNumber, double remainingBalance) {
        this.studentName = studentName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.remainingBalance = remainingBalance;
    }

    // Getters and setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
