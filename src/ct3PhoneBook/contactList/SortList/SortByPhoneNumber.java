package ct3PhoneBook.contactList.SortList;

import ct3PhoneBook.contactObjects.Person;

import java.util.Comparator;

/*
* Sort according to phone number first, then according to name
* */
public class SortByPhoneNumber implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        int ans = person1.getPhoneNumber().compareTo(person2.getPhoneNumber());
        if (ans != 0) {
            return ans;
        }
        else {
            return person1.getName().compareTo(person2.getName());
        }
    }
}
