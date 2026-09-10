package com.loan.approval;

import java.util.ArrayList;
import java.util.List;

public class LoanApprovalService {

    private static final int MINIMUM_AGE = 21;
    private static final double MINIMUM_INCOME = 25000;
    private static final int MINIMUM_CREDIT_SCORE = 650;
    private static final double MAXIMUM_DTI = 40.0;

    public CreditAssessment assessLoan(LoanApplication application)
            throws InvalidLoanDataException {

        validateApplication(application);

        Customer customer = application.getCustomer();

        List<String> reasons = new ArrayList<>();

        // Calculate DTI
        double dti = (customer.getExistingLoanObligation()
                / customer.getMonthlyIncome()) * 100;

        // Maximum permissible loan amount
        double maximumLoanAmount = customer.getMonthlyIncome() * 10;

        // Check all conditions
        if (customer.getAge() < MINIMUM_AGE) {
            reasons.add("Customer must be at least 21 years old.");
        }

        if (!isValidGovernmentId(customer.getGovernmentId())) {
            reasons.add("Invalid government-issued identification number.");
        }

        if (customer.getMonthlyIncome() < MINIMUM_INCOME) {
            reasons.add("Monthly income is below the minimum threshold of ₹25,000.");
        }

        if (customer.getCreditScore() < MINIMUM_CREDIT_SCORE) {
            reasons.add("Credit score is below the minimum requirement of 650.");
        }

        if (dti > MAXIMUM_DTI) {
            reasons.add("Debt-to-income ratio exceeds the maximum permissible 40%.");
        }

        if (application.getRequestedLoanAmount() > maximumLoanAmount) {
            reasons.add("Requested loan amount exceeds the maximum permissible amount.");
        }

        // Critical financial conditions
        boolean criticalFailure =
                customer.getMonthlyIncome() < MINIMUM_INCOME
                || customer.getCreditScore() < MINIMUM_CREDIT_SCORE
                || dti > MAXIMUM_DTI
                || application.getRequestedLoanAmount() > maximumLoanAmount;

        // Rejected
        if (!reasons.isEmpty()) {

            RiskLevel risk;

            if (criticalFailure) {
                risk = RiskLevel.HIGH;
            } else {
                risk = RiskLevel.MEDIUM;
            }

            return new CreditAssessment(
                    LoanStatus.REJECTED,
                    risk,
                    dti,
                    maximumLoanAmount,
                    reasons
            );
        }

        // Low Risk
        if (customer.getCreditScore() >= 750 && dti <= 30) {

            return new CreditAssessment(
                    LoanStatus.APPROVED,
                    RiskLevel.LOW,
                    dti,
                    maximumLoanAmount,
                    reasons
            );
        }

        // Medium Risk
        return new CreditAssessment(
                LoanStatus.CONDITIONALLY_APPROVED,
                RiskLevel.MEDIUM,
                dti,
                maximumLoanAmount,
                reasons
        );
    }

    private boolean isValidGovernmentId(String governmentId) {

        if (governmentId == null) {
            return false;
        }

        return governmentId.matches("[A-Za-z0-9]{6,20}");
    }

    private void validateApplication(LoanApplication application)
            throws InvalidLoanDataException {

        if (application == null) {
            throw new InvalidLoanDataException(
                    "Loan application cannot be null.");
        }

        if (application.getCustomer() == null) {
            throw new InvalidLoanDataException(
                    "Customer details cannot be null.");
        }

        Customer customer = application.getCustomer();

        if (customer.getCustomerId() == null ||
            customer.getCustomerId().isBlank()) {

            throw new InvalidLoanDataException(
                    "Customer ID cannot be empty.");
        }

        if (customer.getName() == null ||
            customer.getName().isBlank()) {

            throw new InvalidLoanDataException(
                    "Customer name cannot be empty.");
        }

        if (customer.getAge() <= 0) {
            throw new InvalidLoanDataException(
                    "Age must be greater than zero.");
        }

        if (customer.getMonthlyIncome() <= 0) {
            throw new InvalidLoanDataException(
                    "Monthly income must be greater than zero.");
        }

        if (customer.getCreditScore() < 300 ||
            customer.getCreditScore() > 850) {

            throw new InvalidLoanDataException(
                    "Credit score must be between 300 and 850.");
        }

        if (customer.getExistingLoanObligation() < 0) {
            throw new InvalidLoanDataException(
                    "Existing loan obligation cannot be negative.");
        }

        if (application.getRequestedLoanAmount() <= 0) {
            throw new InvalidLoanDataException(
                    "Requested loan amount must be greater than zero.");
        }
    }
}
