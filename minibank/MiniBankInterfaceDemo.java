
interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}

interface InterestBearing {
    double interestRate();

    long getBalance();

    default double yearlyInterest() {
        return getBalance() * interestRate() / 100.0;
    }
}

interface WithdrawRule {
    boolean allow(Account account, long amount);
}

interface Premium {
}

class Account implements Transactable, InterestBearing, Premium {
    private long balance;
    private final double rate;

    Account(long balance, double rate) {
        this.balance = balance;
        this.rate = rate;
    }

    @Override
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    @Override
    public double interestRate() {
        return rate;
    }

    @Override
    public long getBalance() {
        return balance;
    }
}

public class MiniBankInterfaceDemo {
    public static void main(String[] args) {
        Account account = new Account(10000, 4.0);

        System.out.println("Initial balance: Rs. "
                + account.getBalance());

        account.deposit(2000);
        System.out.println("After deposit: Rs. "
                + account.getBalance());

        System.out.println("Yearly interest: Rs. "
                + account.yearlyInterest());

        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account a, long amount) {
                return a.withdraw(amount);
            }
        };

        WithdrawRule lambdaRule =
                (a, amount) -> a.withdraw(amount);

        System.out.println("Anonymous withdrawal: "
                + anonymousRule.allow(account, 3000));

        System.out.println("Lambda withdrawal: "
                + lambdaRule.allow(account, 2000));

        System.out.println("Final balance: Rs. "
                + account.getBalance());

        System.out.println("Is Premium: "
                + (account instanceof Premium));
    }
}
