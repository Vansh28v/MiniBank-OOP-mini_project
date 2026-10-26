package service;

import java.util.Scanner;
import model.Account;
import util.AnnotationValidator;

public class AnnotationDemo {
    public static void main(String[] args) { run(new Scanner(System.in)); }

    public static void run(Scanner sc) {
        long balance; double rate;
        while (true) {
            System.out.print("Enter account balance (Rs.): ");
            try { balance = Long.parseLong(sc.nextLine().trim()); break; }
            catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
        }
        while (true) {
            System.out.print("Enter annual interest rate (%): ");
            try { rate = Double.parseDouble(sc.nextLine().trim()); break; }
            catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
        }
        Account account = new Account(balance, rate);
        System.out.println("\n--- Annotation Validation ---");
        System.out.println("Account number: " + account.getAccountNumber());
        System.out.println("Balance: Rs. " + account.getBalance());
        System.out.println("Interest rate: " + account.interestRate() + "%");
        String[] errors = AnnotationValidator.validate(account);
        if (errors.length == 0) System.out.println("No validation errors.");
        else { System.out.println("Validation errors:"); for (String error : errors) System.out.println("- " + error); }
    }
}
