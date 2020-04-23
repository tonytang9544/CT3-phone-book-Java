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
                    continue;
                case ADD_ENTRY:
                    AddEntryPage.addEntryPage(contactList);
                    continue;
                case DELETE_ENTRY:
                    DeleteContact.deleteEntry(contactList);
                    continue;
                case LIST_ALL_ENTRIES:
                    FormatPrinter.printContacts(contactList);
                    continue;
                case FIND_ENTRY:
                    FindContact.findEntry(contactList);
                    continue;
                case QUIT_PROGRAM:
                    if (CommandLineUI.isUserSure()) {
                        printExitMsg();
                        return;
                    }
                    else {
                        continue;
                    }
                case IMPORT_FROM_FILE:
                    try{
                        contactList = VcfParser.parseVcf(
                                "/home/tony/Downloads/Anna.vcf");
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                default:
                    continue;
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
