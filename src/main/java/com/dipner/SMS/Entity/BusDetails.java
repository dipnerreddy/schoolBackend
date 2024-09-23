package com.dipner.SMS.Entity;

import jakarta.persistence.*;

@Entity
public class BusDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber; // Assuming busNumber represents the bus name
    private double busFee;
    private int totalStudents;
    private double totalPendingFee;

    // Getters and setters

    public BusDetails() {
    }

    public BusDetails(Long id, String busNumber, double busFee, int totalStudents, double totalPendingFee) {
        this.id = id;
        this.busNumber = busNumber;
        this.busFee = busFee;
        this.totalStudents = totalStudents;
        this.totalPendingFee = totalPendingFee;
    }

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

    public double getBusFee() {
        return busFee;
    }

    public void setBusFee(double busFee) {
        this.busFee = busFee;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public double getTotalPendingFee() {
        return totalPendingFee;
    }

    public void setTotalPendingFee(double totalPendingFee) {
        this.totalPendingFee = totalPendingFee;
    }
}

