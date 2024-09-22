package com.dipner.SMS.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workerName;
    private LocalDate dob;
    private int yearJoined;
    private String workerNumber;
    private boolean stillWorking;
    private String gender;
    private int age;
    private double salary;
    private String address;

    public Worker() {
    }

    public Worker(Long id, String workerName, LocalDate dob, int yearJoined, String workerNumber, boolean stillWorking, String gender, int age, double salary, String address) {
        this.id = id;
        this.workerName = workerName;
        this.dob = dob;
        this.yearJoined = yearJoined;
        this.workerNumber = workerNumber;
        this.stillWorking = stillWorking;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
        this.address = address;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getYearJoined() {
        return yearJoined;
    }

    public void setYearJoined(int yearJoined) {
        this.yearJoined = yearJoined;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public boolean isStillWorking() {
        return stillWorking;
    }

    public void setStillWorking(boolean stillWorking) {
        this.stillWorking = stillWorking;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


