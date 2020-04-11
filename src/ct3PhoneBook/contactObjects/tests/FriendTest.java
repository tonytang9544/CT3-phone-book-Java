package ct3PhoneBook.contactObjects.tests;

import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import org.junit.jupiter.api.Test;

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
        assertTrue(friend instanceof Friend);
        assertEquals("Tony", friend.getName());
        assertEquals("Tony is nobody.", friend.getShortNotes());
    }
}