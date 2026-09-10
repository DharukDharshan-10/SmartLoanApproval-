package com.loan.approval;

public class LoanApplication {

    private String applicationId;
    private Customer customer;
    private double requestedLoanAmount;

    public LoanApplication(String applicationId,
                           Customer customer,
                           double requestedLoanAmount) {

        this.applicationId = applicationId;
        this.customer = customer;
        this.requestedLoanAmount = requestedLoanAmount;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }
}
