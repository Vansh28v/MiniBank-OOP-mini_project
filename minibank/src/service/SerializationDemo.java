package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import model.Account;

public class SerializationDemo {
    public static void main(String[] args) { run(new Scanner(System.in)); }

    public static void run(Scanner sc) {
        try {
            System.out.print("How many accounts to serialize (1-100)? ");
            int count = Integer.parseInt(sc.nextLine().trim());
            if (count < 1 || count > 100) { System.out.println("Enter a count from 1 to 100."); return; }
            Account[] accounts = new Account[count];
            for (int i=0; i<count; i++) {
                long balance; double rate;
                System.out.print("Opening balance for account " + (i+1) + ": Rs. ");
                balance = Long.parseLong(sc.nextLine().trim());
                System.out.print("Interest rate for account " + (i+1) + " (%): ");
                rate = Double.parseDouble(sc.nextLine().trim());
                accounts[i] = new Account(balance, rate);
            }
            Path file = Path.of("accounts.dat");
            StatePersister.save(accounts, file);
            Account[] restored = StatePersister.load(file);
            System.out.println("\n--- Restored Accounts ---");
            for (Account a : restored) System.out.println("Account: " + a.getAccountNumber() + " | Balance: Rs. " + a.getBalance() + " | Rate: " + a.interestRate() + "%");
            System.out.println("Serialization round-trip successful. File: " + file.toAbsolutePath());
            System.out.println("\n--- Report Generation ---");
            Path logsFolder = Path.of("logs");
            Files.createDirectories(logsFolder);
            Path source = Path.of("transactions.log");
            if (Files.exists(source)) Files.copy(source, logsFolder.resolve("transactions.log"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            ReportGenerator.generate(logsFolder, Path.of("bank-report.txt"));
        } catch (Exception e) { System.out.println("Serialization/report error: " + e.getMessage()); }
    }
}
