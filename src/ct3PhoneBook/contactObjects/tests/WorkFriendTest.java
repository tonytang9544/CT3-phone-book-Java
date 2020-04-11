package ct3PhoneBook.contactObjects.tests;

import ct3PhoneBook.contactObjects.CompanyPosition;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.contactObjects.WorkFriend;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class WorkFriendTest {

    @Test
    void convertPersonToWorkFriend() {
        var person = new Person("Tony", "07777777777");
        var workFriend = WorkFriend.convertPersonToWorkFriend(person,
                "Tony's",
                CompanyPosition.APPRENTICE);
        assertTrue(workFriend instanceof WorkFriend);
        assertEquals("Tony", workFriend.getName());
        assertEquals(CompanyPosition.APPRENTICE, workFriend.getPosition());
    }
}