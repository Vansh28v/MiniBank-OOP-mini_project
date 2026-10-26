package service.practical5;


abstract class AccountBase {
    protected long balance;

    protected AccountBase(long opening) {
        balance = opening;
    }

    public long getBalance() {
        return balance;
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);
}

class SavingsAccount extends AccountBase {
    private final long minBalance;

    SavingsAccount(long opening, long minBalance) {
        super(opening);
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && balance - amount >= minBalance;
    }
}

class CurrentAccount extends AccountBase {
    private final long overdraftLimit;

    CurrentAccount(long opening, long overdraftLimit) {
        super(opening);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && balance - amount >= -overdraftLimit;
    }
}

class FixedDepositAccount extends AccountBase {
    FixedDepositAccount(long opening) {
        super(opening);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }
}

public class MiniBankPolymorphismDemo {
    private static java.util.Scanner sc;
    private static long read(String prompt) {
        while (true) { System.out.print(prompt); try { long n=Long.parseLong(sc.nextLine().trim()); if(n>=0)return n; } catch(NumberFormatException ignored){} System.out.println("Enter a non-negative whole number."); }
    }
    public static void main(String[] args) { run(new java.util.Scanner(System.in)); }

    public static void run(java.util.Scanner scanner) {
        sc = scanner;
        long opening = read("Opening balance for each account (Rs.): ");
        long minimum = read("Minimum required balance for Savings (Rs.): ");
        long overdraft = read("Overdraft limit for Current Account (Rs.): ");
        long withdrawal = read("Test withdrawal amount (Rs.): ");
        AccountBase[] accounts = { new SavingsAccount(opening, minimum), new CurrentAccount(opening, overdraft), new FixedDepositAccount(opening) };
        String[] names = {"Savings Account", "Current Account", "Fixed Deposit Account"};
        for(int i=0;i<accounts.length;i++) {
            AccountBase account=accounts[i];
            System.out.println("\n"+names[i]);
            System.out.println("Balance: Rs. "+account.getBalance());
            System.out.println("Interest Rate: "+account.interestRate()+"%");
            System.out.println("Can withdraw Rs. "+withdrawal+"? "+account.canWithdraw(withdrawal));
        }
    }
}
