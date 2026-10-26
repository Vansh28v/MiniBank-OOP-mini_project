
package service;

import model.Account;
import model.WithdrawRule;
import model.Premium;

import static util.BankUtils.printHeading;

public class MiniBank {
    public static void main(String[] args) {
        Account account = new Account(10000, 4.0);

        printHeading("MINIBANK");

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

        System.out.println("Anonymous withdrawal: "
                + anonymousRule.allow(account, 3000));

        WithdrawRule lambdaRule =
                (a, amount) -> a.withdraw(amount);

        System.out.println("Lambda withdrawal: "
                + lambdaRule.allow(account, 2000));

        System.out.println("Final balance: Rs. "
                + account.getBalance());

        System.out.println("Is Premium: "
                + (account instanceof Premium));
    }
}
