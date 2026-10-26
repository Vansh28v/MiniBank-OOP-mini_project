
package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Transactionlog {

    public static void append(Path file, String line)
            throws IOException {

        Files.writeString(
            file,
            line + System.lineSeparator(),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }

    public static void main(String[] args) {
        try {
            Path file = Path.of("transactions.log");

            append(file, "DEPOSIT,AC0001,1000");
            append(file, "WITHDRAW,AC0001,200");
            append(file, "DEPOSIT,AC0001,500");

            System.out.println("Transactions logged successfully.");
            System.out.println("Log file: " + file.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error writing transaction log: "
                    + e.getMessage());
        }
    }
}
