package ct3PhoneBook.contactList.SortList;

import ct3PhoneBook.contactObjects.Person;

import java.util.Comparator;

public class SortByPhoneNumber implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getPhoneNumber().compareTo(person2.getPhoneNumber());
    }
}
