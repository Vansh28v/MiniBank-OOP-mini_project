
package service;

import java.util.LinkedList;
import java.util.Queue;

class TransactionBuffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 3;
    private boolean finished = false;

    public synchronized void produce(int value)
            throws InterruptedException {

        while (queue.size() == capacity) {
            wait();
        }

        queue.add(value);
        System.out.println("Produced: " + value);

        notifyAll();
    }

    public synchronized Integer consume()
            throws InterruptedException {

        while (queue.isEmpty() && !finished) {
            wait();
        }

        if (queue.isEmpty() && finished) {
            return null;
        }

        int value = queue.remove();

        System.out.println("Consumed: " + value);
        notifyAll();

        return value;
    }

    public synchronized void finish() {
        finished = true;
        notifyAll();
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args)
            throws InterruptedException {

        TransactionBuffer buffer = new TransactionBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                }

                buffer.finish();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Integer value = buffer.consume();

                    if (value == null) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Producer-consumer completed.");
    }
}
