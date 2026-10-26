package service;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import model.Account;

public class TransactionProcessorDemo {
    public static void main(String[] args) throws InterruptedException { run(new Scanner(System.in)); }

    public static void run(Scanner sc) throws InterruptedException {
        long opening = readLong(sc, "Initial balance (Rs.): ");
        int transactions = (int) readLong(sc, "Number of concurrent deposits (1-1000): ");
        while (transactions < 1 || transactions > 1000) { System.out.println("Choose 1 to 1000."); transactions = (int) readLong(sc, "Number of deposits: "); }
        long amount = readLong(sc, "Amount per deposit (Rs.): ");
        Account account = new Account(opening, 4.0);
        TransactionProcessor processor = new TransactionProcessor();
        CountDownLatch latch = new CountDownLatch(transactions);
        for (int i=1; i<=transactions; i++) { final int id=i; processor.submit(() -> { try { account.deposit(amount); System.out.println("Transaction " + id + " processed by " + Thread.currentThread().getName()); } finally { latch.countDown(); } }); }
        latch.await();
        processor.stop();
        System.out.println("Expected balance: Rs. " + (opening + transactions * amount));
        System.out.println("Final balance: Rs. " + account.getBalance());
        System.out.println("\n--- Deadlock Prevention ---");
        long starting = readLong(sc, "Opening balance for each transfer account (Rs.): ");
        int loops = (int) readLong(sc, "Transfers in each direction (1-100000): ");
        while (loops < 1 || loops > 100000) { System.out.println("Choose 1 to 100000."); loops = (int) readLong(sc, "Transfers in each direction: "); }
        TransferAccount account1 = new TransferAccount(101, starting);
        TransferAccount account2 = new TransferAccount(102, starting);
        final int repetitions = loops;
        Thread t1 = new Thread(() -> { for(int i=0;i<repetitions;i++) TransferAccount.transfer(account1, account2, 1); }, "transfer-1");
        Thread t2 = new Thread(() -> { for(int i=0;i<repetitions;i++) TransferAccount.transfer(account2, account1, 1); }, "transfer-2");
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("Account 101: Rs. " + account1.getBalance());
        System.out.println("Account 102: Rs. " + account2.getBalance());
        System.out.println("Total balance preserved: Rs. " + (account1.getBalance()+account2.getBalance()));
        System.out.println("Deadlock prevented successfully.");
    }
    private static long readLong(Scanner sc, String prompt) { while(true) { System.out.print(prompt); try { long n=Long.parseLong(sc.nextLine().trim()); if(n>=0)return n; } catch(NumberFormatException ignored){} System.out.println("Enter a non-negative whole number."); } }
}
