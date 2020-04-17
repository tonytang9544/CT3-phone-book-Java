package ct3PhoneBookTest.contactList;

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

    @Test
    void sortByName() {
        Person person = new Person("Tony", "077");
        Friend friend1 = Friend.convertPersonToFriend(
                person,
                new GregorianCalendar(1995, 1,1),
                "some nobody.");
        Friend friend2 = new Friend(
                "Paul",
                "088",
                new GregorianCalendar(1995, 1,1),
                "A nice guy");
        WorkFriend workFriend = new WorkFriend(
                "Mark",
                "077",
                "Mark's",
                CompanyPosition.CEO);
        ContactList contactList = new ContactList();
        contactList.addEntry(person);
        contactList.addEntry(friend1);
        contactList.addEntry(friend2);
        contactList.addEntry(workFriend);
        // Before sorting
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(0)).getName());
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(1)).getName());
        assertEquals("Paul", ((Person)contactList.getEntryByIndex(2)).getName());
        assertEquals("Mark", ((Person)contactList.getEntryByIndex(3)).getName());
        contactList.sortByName();
        // After sorting
        assertEquals("Mark", ((Person)contactList.getEntryByIndex(0)).getName());
        assertEquals("Paul", ((Person)contactList.getEntryByIndex(1)).getName());
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(2)).getName());
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(3)).getName());
    }

    @Test
    void sortByPhoneNumber() {
        Person person = new Person("Tony", "077");
        Friend friend1 = Friend.convertPersonToFriend(
                person,
                new GregorianCalendar(1995, 1,1),
                "some nobody.");
        Friend friend2 = new Friend(
                "Paul",
                "088",
                new GregorianCalendar(1995, 1,1),
                "A nice guy");
        WorkFriend workFriend = new WorkFriend(
                "Mark",
                "077",
                "Mark's",
                CompanyPosition.CEO);
        ContactList contactList = new ContactList();
        contactList.addEntry(person);
        contactList.addEntry(friend1);
        contactList.addEntry(friend2);
        contactList.addEntry(workFriend);
        // Before sorting
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(0)).getName());
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(1)).getName());
        assertEquals("Paul", ((Person)contactList.getEntryByIndex(2)).getName());
        assertEquals("Mark", ((Person)contactList.getEntryByIndex(3)).getName());
        contactList.sortByPhoneNumber();
        // After sorting
        assertEquals("Mark", ((Person)contactList.getEntryByIndex(0)).getName());
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(1)).getName());
        assertEquals("Tony", ((Person)contactList.getEntryByIndex(2)).getName());
        assertEquals("Paul", ((Person)contactList.getEntryByIndex(3)).getName());
    }

    @Test
    void getEntryByIndex() {
        Person person = new Person("Tony", "077");
        Friend friend1 = Friend.convertPersonToFriend(
                person,
                new GregorianCalendar(1995, 1,1),
                "some nobody.");
        Friend friend2 = new Friend(
                "Paul",
                "088",
                new GregorianCalendar(1995, 1,1),
                "A nice guy");
        WorkFriend workFriend = new WorkFriend(
                "Mark",
                "077",
                "Mark's",
                CompanyPosition.CEO);
        ContactList contactList = new ContactList();
        contactList.addEntry(person);
        contactList.addEntry(friend1);
        contactList.addEntry(friend2);
        contactList.addEntry(workFriend);
        // Finished initialization, start testing
        assertEquals("077",
                contactList.getEntryByIndex(0).getPhoneNumber());
        assertEquals("some nobody.",
                ((Friend)contactList.getEntryByIndex(1)).getShortNotes());
        assertEquals("Paul",
                contactList.getEntryByIndex(2).getName());
        assertEquals("Mark's",
                ((WorkFriend)contactList.getEntryByIndex(3)).getOrganization());

    }

    @Test
    void getNumberOfEntries() {
        Person person = new Person("Tony", "077");
        Friend friend1 = Friend.convertPersonToFriend(
                person,
                new GregorianCalendar(1995, 1,1),
                "some nobody.");
        friend1.setPhoneNumber("07777");
        Friend friend2 = new Friend(
                "Paul",
                "088",
                new GregorianCalendar(1995, 1,1),
                "A nice guy");
        WorkFriend workFriend = new WorkFriend(
                "Mark",
                "0777777",
                "Mark's",
                CompanyPosition.CEO);
        ContactList contactList = new ContactList();
        contactList.addEntry(person);
        contactList.addEntry(friend1);
        contactList.addEntry(friend2);
        contactList.addEntry(workFriend);
        // Finished initialization, start testing
        assertEquals(4, contactList.getNumberOfEntries());
    }

    @Test
    void findEntryByString() {
        Person person = new Person("Tony", "077");
        Friend friend1 = Friend.convertPersonToFriend(
                person,
                new GregorianCalendar(1995, 1,1),
                "some nobody.");
        friend1.setPhoneNumber("07777");
        Friend friend2 = new Friend(
                "Paul",
                "088",
                new GregorianCalendar(1995, 1,1),
                "A nice guy");
        WorkFriend workFriend = new WorkFriend(
                "Mark",
                "0777777",
                "Tony's",
                CompanyPosition.CEO);
        ContactList contactList = new ContactList();
        contactList.addEntry(person);
        contactList.addEntry(friend1);
        contactList.addEntry(friend2);
        contactList.addEntry(workFriend);
        // Finished initialization, start testing
        ContactList personsFound = contactList.findEntryByString("Tony");
        assertEquals(3, personsFound.getNumberOfEntries());
        assertEquals("077",
                personsFound.getEntryByIndex(0).getPhoneNumber());
        assertEquals("some nobody.",
                ((Friend)personsFound.getEntryByIndex(1)).getShortNotes());
        assertEquals("Mark",
                personsFound.getEntryByIndex(2).getName());
    }
}