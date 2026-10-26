
package service;

import model.Account;
import java.util.Collections;

public class AccountStoreDemo {

    public static void main(String[] args) {

        AccountStore store = new AccountStore();

        Account a1 = new Account(5000, 4.0);
        Account a2 = new Account(2000, 4.0);
        Account a3 = new Account(8000, 4.0);

        store.add(a1);
        store.add(a2);
        store.add(a3);

        store.record("DEPOSIT,AC0001,1000");
        store.record("WITHDRAW,AC0002,500");

        System.out.println("Searching for account: "
                + a1.getAccountNumber());

        Account found = store.get(a1.getAccountNumber());

        if (found != null) {
            System.out.println("Account found: "
                    + found.getAccountNumber()
                    + ", Balance: Rs. " + found.getBalance());
        }

        System.out.println("\nAccounts sorted by account number:");
        Collections.sort(store.getAll());

        for (Account account : store.getAll()) {
            System.out.println(account.getAccountNumber()
                    + " - Rs. " + account.getBalance());
        }

        System.out.println("\nAccounts sorted by balance:");
        for (Account account : store.sortedByBalance()) {
            System.out.println(account.getAccountNumber()
                    + " - Rs. " + account.getBalance());
        }

        System.out.println("\nTransaction History:");
        for (String transaction : store.getHistory()) {
            System.out.println(transaction);
        }
    }
}
