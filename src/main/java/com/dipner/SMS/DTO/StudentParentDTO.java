package com.dipner.SMS.DTO;

import java.time.LocalDate;

public class StudentParentDTO {
    private Long parentId;
    private String studentName;
    private LocalDate dob;
    private int admissionYear;
    private String currentClass;
    private boolean stillStudying;
    private String gender;

    // Getters and Setters

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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
}
