package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.*;

import java.util.GregorianCalendar;

public class FormatPrinter {

    public static void printContacts(ContactList contacts) {
        int numberOfEntries = contacts.getNumberOfEntries();
        if (numberOfEntries >= 1) {
            for (int i = 0; i < numberOfEntries; i++) {
                formatPrint(contacts.getEntryByIndex(i));
            }
            System.out.println("");
            System.out.println("End of the list.");
            System.out.println("");
        }
        else {
            System.out.println("");
            System.out.println("The list is empty!");
            System.out.println("");
        }
    }

    public static void formatPrint(Person person) {
        if (person instanceof Friend) {
            printFrient((Friend) person);
        }
        else if (person instanceof WorkFriend) {
            printWorkFriend((WorkFriend) person);
        }
        else {
            printPerson(person);
        }
    }

    private static void printFrient(Friend friend) {
        printPerson(friend);
        System.out.println("Birthday: "
                + Friend.birthdayToDashedString(friend.getBirthday()));
        System.out.println("Short Note: " + friend.getShortNotes());
    }

    private static void printWorkFriend(WorkFriend workFriend) {
        printPerson(workFriend);
        System.out.println("Organization: " + workFriend.getOrganization());
        System.out.println("Company Position: " + workFriend.getPosition().name());
    }

    private static void printPerson(Person person) {
        System.out.println("");
        System.out.println("ID in Phone Book: " + person.getID());
        System.out.println("Name: " + person.getName());
        System.out.println("Phone Number: " + person.getPhoneNumber());
    }
}
