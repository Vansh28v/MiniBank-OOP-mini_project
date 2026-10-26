
package service;

import model.Account;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AccountStore {

    private final HashMap<String, Account> accounts =
            new HashMap<>();

    private final ArrayList<String> history =
            new ArrayList<>();

    public void add(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = getAll();

        list.sort(Comparator.comparingLong(Account::getBalance));

        return list;
    }

    public void record(String transaction) {
        history.add(transaction);
    }

    public ArrayList<String> getHistory() {
        return new ArrayList<>(history);
    }
}
