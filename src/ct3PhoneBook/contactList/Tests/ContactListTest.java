package ct3PhoneBook.contactList.Tests;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.*;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class ContactListTest {

    @Test
    void addEntry() {
        Person person = new Person("Tony", "07777777777");
        Friend friend = Friend.convertPersonToFriend(
                person,
                new GregorianCalendar(1995, 1,1),
                "some nobody.");
        WorkFriend workFriend = WorkFriend.convertPersonToWorkFriend(
                person,
                "Tony's",
                CompanyPosition.APPRENTICE);
        ContactList contactList = new ContactList();
        contactList.addEntry(person);
        contactList.addEntry(friend);
        contactList.addEntry(workFriend);
        assertTrue(contactList.getEntryByIndex(0) instanceof Person);
        assertFalse(contactList.getEntryByIndex(0) instanceof Friend);
        assertTrue(contactList.getEntryByIndex(1) instanceof Friend);
        assertFalse(contactList.getEntryByIndex(1) instanceof WorkFriend);
        assertTrue(contactList.getEntryByIndex(2) instanceof WorkFriend);
    }

}