package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.fileLoaderSaver.VcfParser;

public class CommandLineMainPage {

    public static void mainPage() {
        ContactList contactList = new ContactList();
        while (true) {
            printMainPageTitle(contactList.getNumberOfEntries());
            UserCommand command = CommandLineUI.getUserCommand();
            switch (command) {
                case ILLEGAL_COMMAND:
                    CommandLineUI.printErrorInputMsg();
                    break;
                case ADD_ENTRY:
                    AddEntryPage.addEntryPage(contactList);
                    break;
                case DELETE_ENTRY:
                    DeleteContact.deleteEntry(contactList);
                    break;
                case LIST_ALL_ENTRIES:
                    FormatPrinter.printContacts(contactList);
                    break;
                case FIND_ENTRY:
                    FindContact.findEntry(contactList);
                    break;
                case QUIT_PROGRAM:
                    if (CommandLineUI.isUserSure()) {
                        printExitMsg();
                        return;
                    }
                    else {
                        break;
                    }
                case IMPORT_FROM_FILE:
                    ImportPage.importFromVCF(contactList);
                    break;
                case EXPORT_TO_FILE:
                    ExportPage.exportVcard(contactList);
                    break;
                default:
                    break;
            }
        }
    }

    private static void printMainPageTitle(long numberOfEntries) {
        System.out.println("The phone book has: "
                + numberOfEntries + " entries.");
        System.out.println("What do you want to do?");
        System.out.println("add = Add a entry; del = Delete an entry; "
                + "find = find entry by keyword; list = List all entries; "
                + "quit = quit the program; import = import from vCard file;"
                + "export = export to vCard file");
    }

    private static void printExitMsg() {
        System.out.println("Thanks for using CT3-PhoneBook-Java in console!");
    }



}
