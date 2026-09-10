package com.loan.approval;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LoanApprovalService service = new LoanApprovalService();

        System.out.print("Enter number of loan applications: ");
        int numberOfApplications = scanner.nextInt();

        for (int i = 1; i <= numberOfApplications; i++) {

            System.out.println("\n========== APPLICATION " + i + " ==========");

            scanner.nextLine();

            System.out.print("Customer ID: ");
            String customerId = scanner.nextLine();

            System.out.print("Customer Name: ");
            String name = scanner.nextLine();

            System.out.print("Age: ");
            int age = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Government ID: ");
            String governmentId = scanner.nextLine();

            System.out.print("Monthly Income: ");
            double monthlyIncome = scanner.nextDouble();

            System.out.print("Credit Score: ");
            int creditScore = scanner.nextInt();

            System.out.print("Existing Loan Obligation: ");
            double existingLoan = scanner.nextDouble();

            System.out.print("Requested Loan Amount: ");
            double requestedAmount = scanner.nextDouble();

            Customer customer = new Customer(
                    customerId,
                    name,
                    age,
                    governmentId,
                    monthlyIncome,
                    creditScore,
                    existingLoan
            );

            LoanApplication application = new LoanApplication(
                    "APP-" + i,
                    customer,
                    requestedAmount
            );

            try {

                CreditAssessment assessment =
                        service.assessLoan(application);

                System.out.println("\n---------- CREDIT ASSESSMENT ----------");

                System.out.println("Customer: " + customer.getName());

                System.out.printf(
                        "Debt-to-Income Ratio: %.2f%%%n",
                        assessment.getDti()
                );

                System.out.printf(
                        "Maximum Permissible Loan: ₹%.2f%n",
                        assessment.getMaximumLoanAmount()
                );

                System.out.println(
                        "Loan Status: " + assessment.getStatus()
                );

                System.out.println(
                        "Risk Level: " + assessment.getRiskLevel()
                );

                if (!assessment.getReasons().isEmpty()) {

                    System.out.println("Reasons:");

                    for (String reason : assessment.getReasons()) {
                        System.out.println("- " + reason);
                    }
                }

            } catch (InvalidLoanDataException e) {

                System.out.println(
                        "Invalid application: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }
}
