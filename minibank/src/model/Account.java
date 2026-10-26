
package model;

import java.io.Serializable;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

public class Account implements Transactable, InterestBearing, Serializable, Comparable<Account>  {
    private static final long serialVersionUID = 1L;

    private static int counter = 1;

    @Id
    @MaxLength(10)
    private final String accountNumber;

    @Positive
    private long balance;

    private final double rate;

    public Account(long balance, double rate) {
        this.accountNumber =
                String.format("AC%04d", counter++);
        this.balance = balance;
        this.rate = rate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

   @Override
public synchronized void deposit(long amount) {
    if (amount > 0) {
        balance += amount;
    } else {
        System.out.println("Invalid deposit amount.");
    }
}

    @Override
public synchronized boolean withdraw(long amount) {
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

    @Override
    public int compareTo(Account other) {
    return this.accountNumber.compareTo(other.accountNumber);
    }
}
