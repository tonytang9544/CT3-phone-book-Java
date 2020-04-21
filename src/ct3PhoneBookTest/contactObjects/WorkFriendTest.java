package ct3PhoneBookTest.contactObjects;

import ct3PhoneBook.contactObjects.CompanyPosition;
import ct3PhoneBook.contactObjects.Friend;
import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.contactObjects.WorkFriend;
import org.junit.jupiter.api.DisplayName;
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
        assertEquals(person.getID() + 1, workFriend.getID());
        assertEquals("Tony", workFriend.getName());
        assertEquals(CompanyPosition.APPRENTICE, workFriend.getPosition());
    }

    @Test
    @DisplayName("Test toString() method in class WorkFriend")
    void toStringTest() {
        WorkFriend newWorkFriend = new WorkFriend(
                "Tony",
                "07777777777",
                "Tony's",
                CompanyPosition.APPRENTICE);
        assertEquals("Tony 07777777777 Tony's APPRENTICE",
                newWorkFriend.toString());
    }

}