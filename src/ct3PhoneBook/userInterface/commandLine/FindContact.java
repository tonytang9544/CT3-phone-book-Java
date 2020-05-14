package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;

public class FindContact {
    public static void findEntry(ContactList contacts) {
        printFindEntryTitle();
        String userInput = CommandLineUI.getUserInput();
        if (UserCommand.CANCEL_COMMAND
                == CommandLineUI.getUserCommand(userInput)) {
            return;
        }
        ContactList matchedPeople = contacts.findEntryByString(userInput);
        if (0 == matchedPeople.getNumberOfEntries()){
            System.out.println("No contacts found to contain the keyword: "
                                + userInput);
            return;
        }
        else {
            System.out.println("One or more contacts found!");
            FormatPrinter.printContacts(matchedPeople);
            return;
        }
    }

    private static void printFindEntryTitle() {
        CommandLineUI.printTypeCancelToCancel();
        System.out.println("Please enter the keyword: ");
    }
}
