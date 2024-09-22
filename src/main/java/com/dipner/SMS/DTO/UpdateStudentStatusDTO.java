package com.dipner.SMS.DTO;

public class UpdateStudentStatusDTO {
    private String studentName;
    private String phoneNumber;
    private boolean stillStudying;

    // Getters and setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStillStudying() {
        return stillStudying;
    }

    public void setStillStudying(boolean stillStudying) {
        this.stillStudying = stillStudying;
    }
}
