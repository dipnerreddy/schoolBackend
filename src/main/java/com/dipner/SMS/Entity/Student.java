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
    private int age;  // Field for age
    private double fee;  // Field for fee

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean usesBus;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private SchoolClass schoolClass;

    // Constructors, getters, and setters

    public Student() {
    }

    public Student(Long id, String studentName, LocalDate dob, int admissionYear, String currentClass,
                   boolean stillStudying, String gender, int age, double fee, boolean usesBus,
                   Parent parent, SchoolClass schoolClass) {
        this.id = id;
        this.studentName = studentName;
        this.dob = dob;
        this.admissionYear = admissionYear;
        this.currentClass = currentClass;
        this.stillStudying = stillStudying;
        this.gender = gender;
        this.age = age;
        this.fee = fee;
        this.usesBus = usesBus;
        this.parent = parent;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isUsesBus() {
        return usesBus;
    }

    public void setUsesBus(boolean usesBus) {
        this.usesBus = usesBus;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
}
