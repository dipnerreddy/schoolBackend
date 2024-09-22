package com.dipner.SMS.DTO;

public class ClassBalanceDTO {
    private String className;
    private double remainingBalance;

    public ClassBalanceDTO(String className, double remainingBalance) {
        this.className = className;
        this.remainingBalance = remainingBalance;
    }

    // Getters and setters

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
