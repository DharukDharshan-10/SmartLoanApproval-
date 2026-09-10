package com.loan.approval;

import java.util.List;

public class CreditAssessment {

    private LoanStatus status;
    private RiskLevel riskLevel;
    private double dti;
    private double maximumLoanAmount;
    private List<String> reasons;

    public CreditAssessment(LoanStatus status,
                            RiskLevel riskLevel,
                            double dti,
                            double maximumLoanAmount,
                            List<String> reasons) {

        this.status = status;
        this.riskLevel = riskLevel;
        this.dti = dti;
        this.maximumLoanAmount = maximumLoanAmount;
        this.reasons = reasons;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public double getDti() {
        return dti;
    }

    public double getMaximumLoanAmount() {
        return maximumLoanAmount;
    }

    public List<String> getReasons() {
        return reasons;
    }
}
