package com.dipner.SMS.Entity;

import jakarta.persistence.*;

@Entity
public class BusStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber; // Store the bus number
    private String studentName; // Store the student's name
    private String mobileNumber; // Store the parent's mobile number
    private double balanceFee; // Store the balance fee

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "bus_details_id") // Adjust this name to match your database schema
    private BusDetails busDetails;

    // Default constructor
    public BusStudent() {
    }

    // Constructor with parameters
    public BusStudent(Long id, String busNumber, String studentName, String mobileNumber, double balanceFee, Student student, BusDetails busDetails) {
        this.id = id;
        this.busNumber = busNumber;
        this.studentName = studentName;
        this.mobileNumber = mobileNumber;
        this.balanceFee = balanceFee;
        this.student = student;
        this.busDetails = busDetails;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

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

    public double getBalanceFee() {
        return balanceFee;
    }

    public void setBalanceFee(double balanceFee) {
        this.balanceFee = balanceFee;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BusDetails getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(BusDetails busDetails) {
        this.busDetails = busDetails;
    }
}
