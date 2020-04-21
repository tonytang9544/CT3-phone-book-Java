package ct3PhoneBook.userInterface.commandLine;

import java.util.Scanner;

public class CommandLineUI {

    public static void main(String[] args) {
        MainPage.mainPage();
    }

    /**
     * get the input from user. Filter out invalid input
     * @return 'i' if invalid input, command
     */
    public static UserCommand getUserCommand() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        try {
            return UserCommand.ofCode(userInput);
        } catch (IllegalArgumentException e) {
            return UserCommand.ILLEGAL_COMMAND;
        }
    }

    public static boolean isUserSure() {
        while (true) {
            System.out.println("Are you sure? y/n");
            String response = getUserInput();
            if (response.equals("y")
                    || response.equals("Y")
                    || response.equals("yes")) {
                return true;
            }
            else if (response.equals("n")
                    || response.equals("N")
                    || response.equals("no")) {
                return false;
            }
            else {
                printErrorInputMsg();
                continue;
            }
        }

    }

    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void printOperationCanceled() {
        System.out.println("Operation canceled. No changes recorded.");
    }

    public static void printErrorInputMsg() {
        System.out.println("Invalid input! Please try again.");
    }

    public static final void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.println("Error clearing the screen! Error message :"
                    + e.getMessage());
        }
    }
}
