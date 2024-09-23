package com.dipner.SMS.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BusDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber;
    private int busFee;
    private double totalPendingFee;
    private int totalStudents;

    public BusDetails() {
    }

    public BusDetails(Long id, String busNumber, int busFee, double totalPendingFee, int totalStudents) {
        this.id = id;
        this.busNumber = busNumber;
        this.busFee = busFee;
        this.totalPendingFee = totalPendingFee;
        this.totalStudents = totalStudents;
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

    public int getBusFee() {
        return busFee;
    }

    public void setBusFee(int busFee) {
        this.busFee = busFee;
    }

    public double getTotalPendingFee() {
        return totalPendingFee;
    }

    public void setTotalPendingFee(double totalPendingFee) {
        this.totalPendingFee = totalPendingFee;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
}
