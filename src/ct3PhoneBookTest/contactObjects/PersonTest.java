package ct3PhoneBookTest.contactObjects;

import ct3PhoneBook.contactObjects.Person;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing class Person getters and setters")
class PersonTest {

    @org.junit.jupiter.api.Test
    @DisplayName("Testing setPhoneNumber method")
    void setPhoneNumber() {
        Person newPerson = new Person("Tony", "07777777777");
        newPerson.setPhoneNumber("08888888888");
        assertEquals("08888888888", newPerson.getPhoneNumber());
    }

    @org.junit.jupiter.api.Test
    void setName() {
        Person newPerson = new Person("Tony", "07777777777");
        newPerson.setName("Cici");
        assertEquals("Cici", newPerson.getName());
    }

    @org.junit.jupiter.api.Test
    void getPhoneNumber() {
        Person newPerson = new Person("Tony", "07777777777");
        assertEquals("07777777777", newPerson.getPhoneNumber());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Person newPerson = new Person("Tony", "07777777777");
        assertEquals("Tony", newPerson.getName());
    }
}