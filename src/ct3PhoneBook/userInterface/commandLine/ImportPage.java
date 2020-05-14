package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.fileLoaderSaver.VcfParser;

import java.io.File;

public class ImportPage {

    public static void importFromVCF(ContactList contactList) {
        printImportTitle();
        String userInput = CommandLineUI.getUserInput();
        if (CommandLineUI.isUserInputCancel(userInput)) {
            CommandLineUI.printOperationCanceled();
            return;
        }
        if (!isFileValidForExport(userInput)) {
            CommandLineUI.printOperationCanceled();
            return;
        }
        try {
            ContactList contacts = VcfParser.parseVcf(userInput);
            int numberOfImports = contacts.getNumberOfEntries();
            if (0 == numberOfImports) {
                CommandLineUI.printOperationFailed(
                        new Exception("No valid contacts found in this file!"));
            }
            else {
                for (int i = 0; i < numberOfImports; i++) {
                    contactList.addEntry(contacts.getEntryByIndex(i));
                }
                CommandLineUI.printOperationSuccessful(
                        "Successfully imported "
                                + numberOfImports + " contacts");
            }
        }
        catch (Exception e) {
            CommandLineUI.printOperationFailed(e);
        }

    }

    private static void printImportTitle() {
        CommandLineUI.clearConsole();
        CommandLineUI.printTypeCancelToCancel();
        System.out.println("Please enter a file name for import.");
    }

    private static boolean isFileValidForExport(String userInput) {
        File file = new File(userInput);
        if (!file.isFile()) {
            CommandLineUI.printErrorInputMsg(
                    new Exception("File name is invalid!"));
            return false;
        }
        if (!file.exists()) {
            CommandLineUI.printErrorInputMsg(
                    new Exception("File doesnot exist!"));
            return false;
        }
        return true;
    }
}
