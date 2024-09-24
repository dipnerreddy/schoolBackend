package com.dipner.SMS.DTO;

public class BusBalanceDTO {
    private String busNumber;
    private double remainingBalance;

    // Constructor
    public BusBalanceDTO(String busNumber, double remainingBalance) {
        this.busNumber = busNumber;
        this.remainingBalance = remainingBalance;
    }

    // Getters and Setters
    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
