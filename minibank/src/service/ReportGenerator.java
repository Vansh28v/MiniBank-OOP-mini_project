
package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {

    public static void generate(Path logsFolder, Path reportFile)
            throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int totalLines = 0;

        StringBuilder report = new StringBuilder();

        try (DirectoryStream<Path> files =
                     Files.newDirectoryStream(logsFolder, "*.log")) {

            for (Path file : files) {
                if (Files.size(file) == 0) {
                    continue;
                }

                BasicFileAttributes attrs =
                        Files.readAttributes(
                                file, BasicFileAttributes.class);

                try (BufferedReader reader =
                             Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        totalLines++;

                        String[] parts = line.split(",");

                        if (parts.length < 3) {
                            continue;
                        }

                        try {
                            long amount =
                                    Long.parseLong(parts[2].trim());

                            if (parts[0].trim().equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits += amount;
                            } else if (
                                    parts[0].trim()
                                            .equalsIgnoreCase("WITHDRAW")) {
                                totalWithdrawals += amount;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(
                                    "Skipping invalid transaction: " + line);
                        }
                    }
                }

                report.append("Log file: ")
                      .append(file.getFileName()).append("\n")
                      .append("Size: ").append(attrs.size())
                      .append(" bytes\n")
                      .append("Last modified: ")
                      .append(attrs.lastModifiedTime()).append("\n\n");
            }
        }

        report.append("Total transaction lines: ")
              .append(totalLines).append("\n")
              .append("Total deposits: Rs. ")
              .append(totalDeposits).append("\n")
              .append("Total withdrawals: Rs. ")
              .append(totalWithdrawals).append("\n");

        Files.writeString(
                reportFile,
                report.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println(report);
        System.out.println("Report saved to: "
                + reportFile.toAbsolutePath());
    }

    public static void main(String[] args) {
        try {
            Path logsFolder = Path.of("logs");
            Files.createDirectories(logsFolder);

            Path source = Path.of("transactions.log");

            if (Files.exists(source)) {
                Files.copy(
                        source,
                        logsFolder.resolve("transactions.log"),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            generate(logsFolder, Path.of("bank-report.txt"));

        } catch (IOException e) {
            System.out.println("Report error: " + e.getMessage());
        }
    }
}
