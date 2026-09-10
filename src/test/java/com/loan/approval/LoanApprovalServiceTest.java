package com.loan.approval;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LoanApprovalServiceTest {

    LoanApprovalService service = new LoanApprovalService();

    @Test
    void testLowRiskApprovedApplication() throws Exception {

        Customer customer = new Customer(
                "C001",
                "Arun",
                30,
                "GOV12345",
                60000,
                780,
                12000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP001",
                        customer,
                        300000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.APPROVED, result.getStatus());
        assertEquals(RiskLevel.LOW, result.getRiskLevel());
    }

    @Test
    void testMinimumAgeBoundary() throws Exception {

        Customer customer = new Customer(
                "C002",
                "Kumar",
                21,
                "GOV12346",
                50000,
                760,
                10000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP002",
                        customer,
                        250000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.APPROVED, result.getStatus());
    }

    @Test
    void testBelowMinimumAge() throws Exception {

        Customer customer = new Customer(
                "C003",
                "Ravi",
                20,
                "GOV12347",
                50000,
                760,
                10000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP003",
                        customer,
                        200000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.REJECTED, result.getStatus());

        assertTrue(
                result.getReasons()
                        .contains("Customer must be at least 21 years old.")
        );
    }

    @Test
    void testMinimumCreditScoreBoundary() throws Exception {

        Customer customer = new Customer(
                "C004",
                "Suresh",
                30,
                "GOV12348",
                50000,
                650,
                10000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP004",
                        customer,
                        200000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(
                LoanStatus.CONDITIONALLY_APPROVED,
                result.getStatus()
        );
    }

    @Test
    void testMaximumDTIBoundary() throws Exception {

        Customer customer = new Customer(
                "C005",
                "Vijay",
                35,
                "GOV12349",
                50000,
                760,
                20000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP005",
                        customer,
                        250000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(
                LoanStatus.CONDITIONALLY_APPROVED,
                result.getStatus()
        );

        assertEquals(40.0, result.getDti(), 0.01);
    }

    @Test
    void testHighDTI() throws Exception {

        Customer customer = new Customer(
                "C006",
                "Manoj",
                35,
                "GOV12350",
                50000,
                760,
                25000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP006",
                        customer,
                        200000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.REJECTED, result.getStatus());
        assertEquals(RiskLevel.HIGH, result.getRiskLevel());
    }

    @Test
    void testInvalidGovernmentId() throws Exception {

        Customer customer = new Customer(
                "C007",
                "Raj",
                30,
                "123",
                50000,
                750,
                10000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP007",
                        customer,
                        200000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.REJECTED, result.getStatus());

        assertTrue(
                result.getReasons()
                        .contains(
                            "Invalid government-issued identification number."
                        )
        );
    }

    @Test
    void testMultipleRejectionReasons() throws Exception {

        Customer customer = new Customer(
                "C008",
                "Bala",
                19,
                "123",
                15000,
                500,
                10000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP008",
                        customer,
                        500000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.REJECTED, result.getStatus());

        // Multiple reasons should be returned
        assertTrue(result.getReasons().size() >= 4);
    }

    @Test
    void testExcessiveLoanAmount() throws Exception {

        Customer customer = new Customer(
                "C009",
                "Dinesh",
                30,
                "GOV12351",
                40000,
                750,
                5000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP009",
                        customer,
                        500000
                );

        CreditAssessment result =
                service.assessLoan(application);

        assertEquals(LoanStatus.REJECTED, result.getStatus());

        assertEquals(
                400000,
                result.getMaximumLoanAmount(),
                0.01
        );
    }

    @Test
    void testInvalidCreditScore() {

        Customer customer = new Customer(
                "C010",
                "Test",
                30,
                "GOV12352",
                50000,
                900,
                5000
        );

        LoanApplication application =
                new LoanApplication(
                        "APP010",
                        customer,
                        200000
                );

        assertThrows(
                InvalidLoanDataException.class,
                () -> service.assessLoan(application)
        );
    }
}
