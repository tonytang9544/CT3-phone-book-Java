package ct3PhoneBook.fileLoaderSaver;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

public class VcfExporter {
    // Test run the writeContactListToFile method
    public static void main(String[] args) {
        ContactList contactList = new ContactList();
        contactList.addEntry(new Person("Tony", "077"));
        contactList.addEntry(new WorkFriend("Tora", "088",
                "Crick", CompanyPosition.VICE_PRESIDENT));
        String path = "/home/tony/Desktop/2.vcf";
        File file = new File(path);
        System.out.println("Existing file? " + (file.isFile() && file.exists()));
        try {
            writeContactListToFile(contactList, path);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void writeContactListToFile(ContactList contacts, String path)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        if (contacts.getNumberOfEntries() <= 0) {
            return;
        }
        for (int i = 0; i < contacts.getNumberOfEntries(); i++) {
            writer.write(formatOnePerson(contacts.getEntryByIndex(i)));
        }
        writer.close();
    }

    private static String formatOnePerson(Person person) {
        String parsedPerson = "BEGIN:VCARD\n"
                + "VERSION:3.0\n";
        if (person instanceof WorkFriend) {
            parsedPerson += formatWorkFriend((WorkFriend) person);
            parsedPerson += "CATEGORIES:WorkFriend\n";
        }
        else if (person instanceof Friend) {
            parsedPerson += formatFriend((Friend) person);
            parsedPerson += "CATEGORIES:Friend\n";
        }
        else {
            parsedPerson += formatPerson(person);
            parsedPerson += "CATEGORIES:Person\n";
        }
        return parsedPerson + "END:VCARD\n";
    }

    private static String formatPerson(Person person) {
        return "N:;" + person.getName() + ";;;\n"
                + "FN:" + person.getName() + "\n"
                + "TEL;type=CELL:" + person.getPhoneNumber() +"\n";
    }

    private static String formatFriend(Friend friend) {
        GregorianCalendar birthday = friend.getBirthday();
        int year = birthday.get(GregorianCalendar.YEAR);
        int month = birthday.get(GregorianCalendar.MONTH);
        int day = birthday.get(GregorianCalendar.DAY_OF_MONTH);
        String formattedmonth = month < 10 ? "0" + month : Integer.toString(month);
        String formattedday = day < 10 ? "0" + day : Integer.toString(day);
        return formatPerson(friend)
                + "BDAY:" + year + formattedmonth + formattedday + "\n"
                + "NOTE:" + friend.getShortNotes() + "\n";
    }

    private static String formatWorkFriend(WorkFriend workFriend) {
        return formatPerson(workFriend)
                + "ORG:" + workFriend.getOrganization() + "\n"
                + "TITLE:" + workFriend.getPosition().name() + "\n";
    }
}
