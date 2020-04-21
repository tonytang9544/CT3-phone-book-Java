package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;

import java.util.Scanner;

public class AddEntryPage {

    public static final void addEntryPage(ContactList contactList) {
        while (true) {
            CommandLineUI.clearConsole();
            printAddEntryPageTitle();
            String userInput = CommandLineUI.getUserInput();
            if (userInput.equals("quit")) {
                return;
            }
            else if (userInput.equals("Person")
                    || userInput.equals("person")) {
                Person newPerson = getPersonDetails();
                if (null == newPerson) {
                    continue;
                }
                else {
                    contactList.addEntry(newPerson);
                    return;
                }
            }
            else {
                CommandLineUI.printErrorInputMsg();
            }
        }

    }

    private static Person getPersonDetails() {
        System.out.println("Please enter the name of the person: ");
        String name = CommandLineUI.getUserInput();
        System.out.println("Please enter the phone number of the person: ");
        String phoneNumber = CommandLineUI.getUserInput();
        if (CommandLineUI.isUserSure()) {
            return new Person(name, phoneNumber);
        }
        else {
            CommandLineUI.printOperationCanceled();
            return null;
        }
    }

    private static void printAddEntryPageTitle() {
        System.out.println("Type \"quit\" to cancel at any time.");
        System.out.println("Enter the type of the new contact: ");
    }
}
