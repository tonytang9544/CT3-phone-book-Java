package ct3PhoneBook.contactList.SortList;

import ct3PhoneBook.contactObjects.Person;

import java.util.Comparator;

/*
 * Sort according to name first, then according to phone number
 * */
public class SortByName implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        int ans = person1.getName().compareTo(person2.getName());
        if (ans != 0) {
            return ans;
        }
        else {
            return person1.getPhoneNumber().compareTo(person2.getPhoneNumber());
        }
    }
}
