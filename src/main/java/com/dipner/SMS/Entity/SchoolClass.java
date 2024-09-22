package com.dipner.SMS.Entity;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;
    private double fees;
    private int totalStrength;
    private double totalPendingFee;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public int getTotalStrength() {
        return totalStrength;
    }

    public void setTotalStrength(int totalStrength) {
        this.totalStrength = totalStrength;
    }

    public double getTotalPendingFee() {
        return totalPendingFee;
    }

    public void setTotalPendingFee(double totalPendingFee) {
        this.totalPendingFee = totalPendingFee;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
