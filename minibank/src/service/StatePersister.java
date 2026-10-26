
package service;

import model.Account;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class StatePersister {

    public static void save(Account[] accounts, Path file)
            throws IOException {

        try (ObjectOutputStream out =
                new ObjectOutputStream(Files.newOutputStream(file))) {

            out.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        }
    }

    public static Account[] load(Path file)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream in =
                new ObjectInputStream(Files.newInputStream(file))) {

            return (Account[]) in.readObject();
        }
    }
}
