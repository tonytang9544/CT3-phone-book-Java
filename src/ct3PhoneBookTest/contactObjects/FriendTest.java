package ct3PhoneBookTest.contactObjects;

import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    @Test
    void convertPersonToFriend() {
        var person = new Person("Tony", "07777777777");
        GregorianCalendar birthday = new GregorianCalendar();
        birthday.set(1995, 1,1);
        var friend = Friend.convertPersonToFriend(person, birthday,
                "Tony is nobody.");
        assertEquals(person.getID() + 1, friend.getID());
        assertEquals("Tony", friend.getName());
        assertEquals("Tony is nobody.", friend.getShortNotes());
    }

    @Test
    @DisplayName("Test toString() method in class Friend")
    void toStringTest() {
        GregorianCalendar birthday = new GregorianCalendar();
        birthday.set(1995, 0, 31);
        Friend newFriend = new Friend(
                "Tony",
                "07777777777",
                birthday,
                "Tony is boring.");
        assertEquals("Tony 07777777777 1995-1-31 Tony is boring.",
                newFriend.toString());
    }
}