package service;

import java.util.Scanner;
import model.Account;

public class ThreadSafetyDemo {
    public static void main(String[] args) throws InterruptedException { run(new Scanner(System.in)); }

    public static void run(Scanner sc) throws InterruptedException {
        long opening = readLong(sc, "Initial balance (Rs.): ");
        int workers = (int) readLong(sc, "Number of threads (1-100): ");
        while (workers < 1 || workers > 100) { System.out.println("Choose 1 to 100 threads."); workers = (int) readLong(sc, "Number of threads: "); }
        long deposit = readLong(sc, "Deposit per thread (Rs.): ");
        int repetitions = (int) readLong(sc, "Deposits per thread (1-100000): ");
        while (repetitions < 1 || repetitions > 100000) { System.out.println("Choose 1 to 100000 repetitions."); repetitions = (int) readLong(sc, "Deposits per thread: "); }
        Account account = new Account(opening, 4.0);
        Thread[] threads = new Thread[workers];
        for (int i=0; i<workers; i++) { threads[i] = new Thread(new AccountWorker(account, repetitions, deposit), "worker-"+(i+1)); threads[i].start(); }
        for (Thread thread : threads) thread.join();
        long expected = opening + workers * (long) repetitions * Math.max(0, deposit);
        System.out.println("Expected balance: Rs. " + expected);
        System.out.println("Final balance: Rs. " + account.getBalance());
    }
    private static long readLong(Scanner sc, String prompt) { while(true) { System.out.print(prompt); try { long n=Long.parseLong(sc.nextLine().trim()); if(n>=0) return n; } catch(NumberFormatException ignored) {} System.out.println("Enter a non-negative whole number."); } }
}
