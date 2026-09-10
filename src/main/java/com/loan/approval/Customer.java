package com.loan.approval;

public class Customer {

    private String customerId;
    private String name;
    private int age;
    private String governmentId;
    private double monthlyIncome;
    private int creditScore;
    private double existingLoanObligation;

    public Customer(String customerId, String name, int age,
                    String governmentId, double monthlyIncome,
                    int creditScore, double existingLoanObligation) {

        this.customerId = customerId;
        this.name = name;
        this.age = age;
        this.governmentId = governmentId;
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
        this.existingLoanObligation = existingLoanObligation;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public double getExistingLoanObligation() {
        return existingLoanObligation;
    }
}
