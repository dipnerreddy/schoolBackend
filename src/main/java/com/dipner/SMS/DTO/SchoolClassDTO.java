package com.dipner.SMS.DTO;


public class SchoolClassDTO {
    private String className;
    private double fees;
    private int totalStrength;
    private double totalPendingFee;

    // Getters and Setters

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
}
