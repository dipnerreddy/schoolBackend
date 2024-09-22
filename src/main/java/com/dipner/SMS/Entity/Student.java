package com.dipner.SMS.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private LocalDate dob;
    private int admissionYear;
    private String currentClass;
    private boolean stillStudying;
    private String gender;
    private int age;  // New field for age
    private double fee;  // New field for fee


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private SchoolClass schoolClass;





    // Getters and Setters

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }


    // Existing getters and setters

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }


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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
