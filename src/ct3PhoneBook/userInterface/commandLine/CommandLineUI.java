package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;

import java.util.Scanner;

public class CommandLineUI {

    private ContactList contactList = new ContactList();
    private Scanner keyscanner = new Scanner(System.in);
    private UserCommand currentCommand;

    public final void start() {
        mainPage();
    }

    private void mainPage() {
        while (true) {
            printMainPageTitle();
            this.currentCommand = getUserCommand();
            switch (this.currentCommand) {
                case ILLEGAL_COMMAND:
                    printErrorInputMsg();
                    break;
                case ADD_ENTRY:

            }
        }
    }

    private boolean addEntryPage() {
        clearConsole();
        printAddEntryPageTitle();
        String type = keyscanner.nextLine();
        switch (type) {
            case "person":

        }
    }

    private static void printAddEntryPageTitle() {
        System.out.println("Press c to cancel at any time.");
        System.out.println("Enter the type of the new contact: ");
    }

    /**
     * get the input from user. Filter out invalid input
     * @return 'i' if invalid input, command
     */
    private UserCommand getUserCommand() {
        String userInput = keyscanner.nextLine();
        try {
            return UserCommand.ofCode(userInput);
        } catch (IllegalArgumentException e) {
            return UserCommand.ILLEGAL_COMMAND;
        }
    }

    private void printMainPageTitle() {
        System.out.println("The phone book has: "
                + this.contactList.getNumberOfEntries() + "entries.");
        System.out.println("What do you want to do?");
        System.out.println("a = Add a entry; d = Delete an entry; " +
                "f = find entry by keyword; l = List all entries");
    }

    private static void printErrorInputMsg() {
        System.out.println("Invalid input! Please try again.");
    }

    private static void clearConsole() {
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
