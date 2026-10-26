
package service;

import model.Account;

public class RepositoryDemo {

    public static void main(String[] args) {

        Repository<Account> repository = new Repository<>();

        Account a1 = new Account(5000, 4.0);
        Account a2 = new Account(2000, 4.0);
        Account a3 = new Account(8000, 4.0);

        repository.saveAll(a1, a2, a3);

        System.out.println("All Accounts:");
        for (Account account : repository.findAll()) {
            System.out.println(
                    account.getAccountNumber()
                    + " - Rs. " + account.getBalance());
        }

        String id = a1.getAccountNumber();
        Account found = repository.findById(id);

        System.out.println("\nSearching for: " + id);
        if (found != null) {
            System.out.println("Account found: Rs. " + found.getBalance());
        } else {
            System.out.println("Account not found.");
        }

        repository.delete(a2.getAccountNumber());
        System.out.println("\nAccounts after deletion: "
                + repository.findAll().size());

        System.out.println("\nAverage: "
                + repository.average(10, 20, 30, 40));
    }
}
