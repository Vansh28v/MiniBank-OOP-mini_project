
import java.util.Scanner;

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

record BankInfo(String name, String branch) {}

public class MiniBank {
    public static void main(String[] args) {

        BankInfo bank = new BankInfo(
            "MiniBank", "CHARUSAT Branch"
        );

        System.out.println("Welcome to " + bank.name());
        System.out.println("Branch: " + bank.branch());

        try (Scanner sc = new Scanner(System.in)) {
            MenuOption[] options = MenuOption.values();

            while (true) {
                System.out.println("\n===== MINIBANK MENU =====");

                for (int i = 0; i < options.length; i++) {
                    System.out.println((i + 1) + ". " + options[i]);
                }

                System.out.print("Enter your choice: ");

                if (!sc.hasNextInt()) {
                    sc.next();
                    System.out.println("Invalid input! Enter a number.");
                    continue;
                }

                int choice = sc.nextInt();

                if (choice < 1 || choice > options.length) {
                    System.out.println("Invalid choice! Try again.");
                    continue;
                }

                MenuOption option = options[choice - 1];

                String message = switch (option) {
                    case OPEN_ACCOUNT -> "Open account - later practical";
                    case DEPOSIT -> "Deposit - later practical";
                    case WITHDRAW -> "Withdraw - later practical";
                    case TRANSFER -> "Transfer - later practical";
                    case EXIT -> "Thank you for using MiniBank. Goodbye!";
                };

                System.out.println(message);

                if (option == MenuOption.EXIT) {
                    break;
                }
            }
        }
    }
}
