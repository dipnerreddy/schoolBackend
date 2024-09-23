package com.dipner.SMS.DTO;

import java.time.LocalDate;

public class StudentParentDTO {
    private String studentName;
    private LocalDate dob;
    private int admissionYear;
    private String currentClass;
    private boolean stillStudying;
    private String gender;

    private String parentName;
    private String parentPhoneNumber;
    private String parentAddress;

    // New fields for bus details
    private boolean usesBus;
    private String busNumber;
    private double busFee;

    // Getters and setters

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public boolean isStillStudying() {
        return stillStudying;
    }

    public void setStillStudying(boolean stillStudying) {
        this.stillStudying = stillStudying;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    // Getters and setters for new bus details fields

    public boolean isUsesBus() {
        return usesBus;
    }

    public void setUsesBus(boolean usesBus) {
        this.usesBus = usesBus;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public double getBusFee() {
        return busFee;
    }

    public void setBusFee(double busFee) {
        this.busFee = busFee;
    }
}
