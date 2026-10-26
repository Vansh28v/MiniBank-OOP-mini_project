
package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import model.Account;
import service.practical2.CustomerAccountDemo;
import service.practical3.CustomerAccountMethodsDemo;
import service.practical4.MiniBankValidationDemo;
import service.practical5.MiniBankPolymorphismDemo;

public class BankingApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Path DATA_FILE = Path.of("accounts.dat");

    private static AccountStore store = new AccountStore();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n================================");
            System.out.println("          MINIBANK");
            System.out.println("================================");
            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Check Balance");
            System.out.println("6. Transfer Money");
            System.out.println("7. Transaction History");
            System.out.println("8. Sort Accounts by Balance");
            System.out.println("9. Save Accounts");
            System.out.println("10. Load Accounts");
            System.out.println("11. Generate Bank Report");
            System.out.println("12. Practical Demonstrations");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewAccounts();
                case 3 -> depositMoney();
                case 4 -> withdrawMoney();
                case 5 -> checkBalance();
                case 6 -> transferMoney();
                case 7 -> showHistory();
                case 8 -> sortAccounts();
                case 9 -> saveAccounts();
                case 10 -> loadAccounts();
                case 11 -> generateReport();
                case 12 -> practicalDemonstrations();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using MiniBank!");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    private static void createAccount() {
        System.out.print("Enter opening balance: Rs. ");
        long balance = readLong();

        if (balance < 0) {
            System.out.println("Opening balance cannot be negative.");
            return;
        }

        System.out.print("Enter annual interest rate (%): ");
        double rate = readDouble();

        if (!Double.isFinite(rate) || rate < 0) {
            System.out.println("Interest rate must be a valid non-negative number.");
            return;
        }

        Account account = new Account(balance, rate);
       store.add(account);

logTransaction("CREATE", account.getAccountNumber(), balance);

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Balance: Rs. " + account.getBalance());
    }

    private static void viewAccounts() {
        ArrayList<Account> accounts = store.getAll();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("\n--- All Accounts ---");

        for (Account a : accounts) {
            System.out.println(
                a.getAccountNumber() + " | Balance: Rs. "
                + a.getBalance() + " | Interest: "
                + a.interestRate() + "%"
            );
        }
    }

    private static Account findAccount() {
        System.out.print("Enter account number (e.g. AC0001): ");
        String number = sc.nextLine().trim();

        Account account = store.get(number);

        if (account == null) {
            System.out.println("Account not found.");
        }

        return account;
    }

    private static void depositMoney() {
        Account account = findAccount();
        if (account == null) return;

        System.out.print("Enter deposit amount: Rs. ");
        long amount = readLong();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

      account.deposit(amount);
logTransaction("DEPOSIT", account.getAccountNumber(), amount);

        System.out.println("Deposit successful.");
        System.out.println("New balance: Rs. " + account.getBalance());
    }

    private static void withdrawMoney() {
        Account account = findAccount();
        if (account == null) return;

        System.out.print("Enter withdrawal amount: Rs. ");
        long amount = readLong();

        if (account.withdraw(amount)) {
            logTransaction("WITHDRAW", account.getAccountNumber(), amount);

            System.out.println("Withdrawal successful.");
            System.out.println("New balance: Rs. " + account.getBalance());
        } else {
            System.out.println(
                "Withdrawal failed. Check the amount and available balance."
            );
        }
    }

    private static void checkBalance() {
        Account account = findAccount();
        if (account == null) return;

        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Balance: Rs. " + account.getBalance());
        System.out.println("Annual interest rate: "
                + account.interestRate() + "%");
        System.out.println("Yearly interest: Rs. "
                + account.getBalance() * account.interestRate() / 100.0);
    }

    private static void transferMoney() {
        System.out.println("Source account:");
        Account source = findAccount();
        if (source == null) return;

        System.out.print("Enter destination account number: ");
        String destinationNumber = sc.nextLine().trim();
        Account destination = store.get(destinationNumber);

        if (destination == null) {
            System.out.println("Destination account not found.");
            return;
        }

        if (source == destination) {
            System.out.println("Source and destination must be different.");
            return;
        }

        System.out.print("Enter transfer amount: Rs. ");
        long amount = readLong();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        // Roll back the debit if the destination deposit fails.
        synchronized (source) {
            synchronized (destination) {
                if (!source.withdraw(amount)) {
                    System.out.println("Transfer failed. Insufficient balance.");
                    return;
                }

                destination.deposit(amount);
            }
        }

        logTransaction("TRANSFER",
        source.getAccountNumber() + "->" + destination.getAccountNumber(),
        amount);

        System.out.println("Transfer successful.");
    }

    
private static void showHistory() {
    System.out.println("\n--- Transaction History ---");

    Path logFile = Path.of("transactions.log");

    try {
        if (!Files.exists(logFile) || Files.size(logFile) == 0) {
            System.out.println("No transactions found.");
            return;
        }

        for (String line : Files.readAllLines(logFile)) {
            System.out.println(line);
        }

    } catch (IOException e) {
        System.out.println("Error reading transaction history: "
                + e.getMessage());
    }
}


    private static void sortAccounts() {
        ArrayList<Account> accounts = store.sortedByBalance();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("\n--- Accounts Sorted by Balance ---");

        for (Account a : accounts) {
            System.out.println(
                a.getAccountNumber() + " | Rs. " + a.getBalance()
            );
        }
    }

    private static void saveAccounts() {
        Account[] accounts = store.getAll().toArray(new Account[0]);

        try {
            StatePersister.save(accounts, DATA_FILE);
            System.out.println("Saved to: " + DATA_FILE.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not save accounts: " + e.getMessage());
        }
    }

    private static void loadAccounts() {
        if (!Files.exists(DATA_FILE)) {
            System.out.println("No saved account file found. Save accounts first.");
            return;
        }

        try {
            Account[] accounts = StatePersister.load(DATA_FILE);
            AccountStore loadedStore = new AccountStore();

            for (Account account : accounts) {
                loadedStore.add(account);
            }

            store = loadedStore;
            System.out.println("Loaded " + accounts.length + " account(s).");
            System.out.println(
                "Note: transaction history is not restored from this file."
            );
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load accounts: " + e.getMessage());
        }
    }

    private static void generateReport() {
    try {
        Path logsFolder = Path.of("logs");
        Files.createDirectories(logsFolder);

        Path transactionFile = Path.of("transactions.log");

        if (Files.exists(transactionFile)) {
            Files.copy(
                transactionFile,
                logsFolder.resolve("transactions.log"),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        }

        Path reportFile = Path.of("bank-report.txt");
        ReportGenerator.generate(logsFolder, reportFile);

    } catch (IOException e) {
        System.out.println("Report generation failed: " + e.getMessage());
    }
}


private static void practicalDemonstrations() {
    boolean running = true;

    while (running) {
        System.out.println("\n===== PRACTICAL DEMONSTRATIONS =====");
        System.out.println("2. Customer and Account Details");
        System.out.println("3. Customer Address, Cloning and Object Checks");
        System.out.println("4. Input Validation and Account Statement");
        System.out.println("5. Savings, Current and Fixed Deposit");
        System.out.println("7. Annotation-Based Validation");
        System.out.println("9. Thread Safety");
        System.out.println("10. Concurrent Transactions and Deadlock Prevention");
        System.out.println("11. Serialization and Report Generation");
        System.out.println("0. Return to Main Menu");
        System.out.print("Enter your choice: ");

        String input = sc.nextLine().trim();
        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            continue;
        }

        switch (choice) {
            case 2 -> CustomerAccountDemo.run(sc);
            case 3 -> CustomerAccountMethodsDemo.run(sc);
            case 4 -> MiniBankValidationDemo.run(sc);
            case 5 -> MiniBankPolymorphismDemo.run(sc);
            case 7 -> AnnotationDemo.run(sc);
            case 9 -> runInterruptibleDemo(() -> ThreadSafetyDemo.run(sc));
            case 10 -> runInterruptibleDemo(() -> TransactionProcessorDemo.run(sc));
            case 11 -> serializeCurrentAccountsAndReport();
            case 0 -> running = false;
            default -> System.out.println("Invalid choice. Try again.");
        }
    }
}

private static void serializeCurrentAccountsAndReport() {
    try {
        ArrayList<Account> currentAccounts = store.getAll();
        if (currentAccounts.isEmpty()) {
            System.out.println("No accounts in this session. Create an account first to serialize current data.");
        } else {
            Account[] accountArray = currentAccounts.toArray(new Account[0]);
            StatePersister.save(accountArray, DATA_FILE);
            Account[] restored = StatePersister.load(DATA_FILE);
            System.out.println("\n--- Serialized Accounts Restored ---");
            for (Account account : restored) {
                System.out.println(account.getAccountNumber() + " | Balance: Rs. "
                        + account.getBalance() + " | Interest: " + account.interestRate() + "%");
            }
            System.out.println("Restored " + restored.length + " account(s) from " + DATA_FILE.toAbsolutePath());
        }
        System.out.println("\n--- Transaction Report ---");
        generateReport();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Serialization failed: " + e.getMessage());
    }
}


private static void runInterruptibleDemo(RunnableDemo demo) {
    try {
        demo.run();
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Demo was interrupted.");
    }
}

@FunctionalInterface
private interface RunnableDemo {
    void run() throws InterruptedException;
}



    private static void logTransaction(String type,
                                   String accountNumber,
                                   long amount) {
    String line = type + ","
            + accountNumber + ","
            + amount + ","
            + LocalDateTime.now();

    store.record(line);

    try {
        Transactionlog.append(
                Path.of("transactions.log"), line);
    } catch (IOException e) {
        System.out.println(
                "Warning: transaction log could not be saved: "
                + e.getMessage());
    }
}


    private static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static long readLong() {
        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double readDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
