package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;

public class MainPage {

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
                case LIST_ALL_ENTRIES:
                    break;
                case QUIT_PROGRAM:
                    if (CommandLineUI.isUserSure()) {
                        return;
                    }
                    else {
                        continue;
                    }
                default:
                    break;
            }
        }
    }

    private static void printMainPageTitle(long numberOfEntries) {
        System.out.println("The phone book has: "
                + numberOfEntries + " entries.");
        System.out.println("What do you want to do?");
        System.out.println("add = Add a entry; del = Delete an entry; " +
                "find = find entry by keyword; list = List all entries; " +
                "quit = quit the program");
    }

}
