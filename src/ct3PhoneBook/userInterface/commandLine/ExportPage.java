package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.fileLoaderSaver.VcfExporter;

import java.io.File;

public class ExportPage {
    public static void exportVcard(ContactList contacts) {
        printExportTitle();
        String userInput = CommandLineUI.getUserInput();
        if (CommandLineUI.isUserInputCancel(userInput)) {
            CommandLineUI.printOperationCanceled();
            return;
        }
        if (0 == contacts.getNumberOfEntries()) {
            CommandLineUI.printOperationFailed(
                    new Exception("There is no contact in the"
                            + " phone book for export."));
            return;
        }

        try {
            if (!isFileValidForExport(userInput)) {
                CommandLineUI.printOperationCanceled();
                return;
            }
            VcfExporter.writeContactListToFile(contacts, userInput);
        }
        catch (Exception e) {
            CommandLineUI.printErrorInputMsg(e);
            CommandLineUI.printOperationFailed();
            return;
        }
    }

    private static void printExportTitle() {
        CommandLineUI.clearConsole();
        CommandLineUI.printTypeCancelToCancel();
        System.out.println("Please enter a file name for export.");
    }

    private static boolean isFileValidForExport(String userInput) {
        File file = new File(userInput);
        if (!file.isFile()) {
            return false;
        }
        if (file.exists()) {
            System.out.println("File already exist. " +
                    "Continue will overwrite existing file.");
            if (!CommandLineUI.isUserSure()) {
                return false;
            }
        }
        return true;
    }

}
