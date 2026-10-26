package service.practical4;


import java.util.regex.Pattern;

enum TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER
}

record Command(
    TransactionType type,
    String accountNumber,
    long amount
) {}

class Validator {
    private static final Pattern MOBILE =
        Pattern.compile("^[6-9][0-9]{9}$");

    private static final Pattern EMAIL =
        Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PAN =
        Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    private static final Pattern IFSC =
        Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    static boolean isValidMobile(String s) {
        return MOBILE.matcher(s).matches();
    }

    static boolean isValidEmail(String s) {
        return EMAIL.matcher(s).matches();
    }

    static boolean isValidPan(String s) {
        return PAN.matcher(s).matches();
    }

    static boolean isValidIfsc(String s) {
        return IFSC.matcher(s).matches();
    }
}

class CommandParser {
    static Command parse(String line) {
        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                "Expected: TYPE ACCOUNT AMOUNT"
            );
        }

        TransactionType type =
            TransactionType.valueOf(parts[0].toUpperCase());

        long amount = Long.parseLong(parts[2]);

        return new Command(type, parts[1], amount);
    }
}

class Account {
    private final String accountNumber;
    private final String ownerName;
    private long balance;

    Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }
}

class StatementFormatter {
    static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();

        sb.append("===== MINIBANK STATEMENT =====\n");
        sb.append("Account Number: ")
          .append(account.getAccountNumber()).append("\n");
        sb.append("Account Holder: ")
          .append(account.getOwnerName()).append("\n");
        sb.append("Balance: Rs. ")
          .append(account.getBalance()).append("\n");
        sb.append("==============================");

        return sb.toString();
    }
}

public class MiniBankValidationDemo {
    private static java.util.Scanner sc;
    private static String ask(String prompt) { System.out.print(prompt); return sc.nextLine().trim(); }
    public static void main(String[] args) { run(new java.util.Scanner(System.in)); }

    public static void run(java.util.Scanner scanner) {
        sc = scanner;
        System.out.println("--- Input Validation ---");
        String mobile = ask("Enter mobile number: ");
        String email = ask("Enter email: ");
        String pan = ask("Enter PAN (uppercase): ");
        String ifsc = ask("Enter IFSC (uppercase): ");
        System.out.println("Mobile valid: " + Validator.isValidMobile(mobile));
        System.out.println("Email valid: " + Validator.isValidEmail(email));
        System.out.println("PAN valid: " + Validator.isValidPan(pan));
        System.out.println("IFSC valid: " + Validator.isValidIfsc(ifsc));

        System.out.println("\n--- Command Parsing ---");
        System.out.println("Enter command as: DEPOSIT AC0001 500 (types: DEPOSIT, WITHDRAW, TRANSFER)");
        String line = ask("Command: ");
        try {
            Command command = CommandParser.parse(line);
            if (command.amount() <= 0) System.out.println("Command amount must be positive.");
            else System.out.println("Parsed command: " + command);
        } catch (IllegalArgumentException | java.lang.IllegalStateException e) {
            System.out.println("Invalid command: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid command: use a valid type, account number and whole-number amount.");
        }

        System.out.println("\n--- Account Statement ---");
        String accountNumber = ask("Account number: ");
        String owner = ask("Account holder name: ");
        long balance;
        while (true) {
            try { balance = Long.parseLong(ask("Current balance (Rs.): ")); if (balance >= 0) break; }
            catch (NumberFormatException ignored) { }
            System.out.println("Enter a valid non-negative balance.");
        }
        Account account = new Account(accountNumber, owner, balance);
        System.out.println(StatementFormatter.buildStatement(account));
    }
}
