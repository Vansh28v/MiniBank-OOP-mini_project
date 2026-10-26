
package service;

public class DeadlockPreventionDemo {
    public static void main(String[] args)
            throws InterruptedException {

        TransferAccount account1 =
                new TransferAccount(101, 10000);

        TransferAccount account2 =
                new TransferAccount(102, 10000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                TransferAccount.transfer(account1, account2, 10);
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                TransferAccount.transfer(account2, account1, 10);
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nFinal Balances:");
        System.out.println(
                "Account 101: Rs. " + account1.getBalance());
        System.out.println(
                "Account 102: Rs. " + account2.getBalance());
        System.out.println(
                "Total: Rs. "
                + (account1.getBalance() + account2.getBalance()));
        System.out.println("Deadlock prevented successfully.");
    }
}
