package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;

public class DeleteContact {
    public static boolean deleteEntry(ContactList contacts) {
        printDeleteContactTitle();
        String userInput = CommandLineUI.getUserInput();
        if (UserCommand.CANCEL_COMMAND == CommandLineUI.getUserCommand(userInput)) {
            CommandLineUI.printOperationCanceled();
            return false;
        }
        long ID;
        try {
            ID = Long.parseLong(userInput);
        } catch (Exception e) {
            CommandLineUI.printErrorInputMsg(e);
            return false;
        }
        return confirmAndDeleteByID(ID, contacts);
    }

    private static boolean confirmAndDeleteByID(long ID,
                                            ContactList contacts) {
        Person personToDelete = contacts.getPersonByID(ID);
        if (personToDelete != null) {
            System.out.println("Contact to delete: ");
            FormatPrinter.formatPrint(personToDelete);
            if (CommandLineUI.isUserSure()) {
                if (contacts.delEntry(ID)) {
                    CommandLineUI.printOperationSuccessful();
                    return true;
                } else {
                    CommandLineUI.printOperationFailed();
                    return false;
                }
            } else {
                CommandLineUI.printOperationCanceled();
                return false;
            }
        } else {
            CommandLineUI.printErrorInputMsg();
            CommandLineUI.printOperationCanceled();
            return false;
        }
    }

    private static void printDeleteContactTitle() {
        CommandLineUI.printTypeCancelToCancel();
        System.out.println("Please enter the ID of the person you want to delete:");
    }
}
