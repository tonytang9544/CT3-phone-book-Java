package ct3PhoneBook.contactList;

import ct3PhoneBook.contactList.SortList.*;
import ct3PhoneBook.contactObjects.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactList {
    private final List<Person> contactList;

    public ContactList() {
        contactList = new ArrayList<Person>();
    }

    public void addEntry(Person person) {
        contactList.add(person);
    }

    public Person getEntryByIndex(int index) {
        return contactList.get(index);
    }

    public void sortByName() {
        Collections.sort(this.contactList, new SortByName());
    }

    public void sortByPhoneNumber() {
        Collections.sort(this.contactList, new SortByPhoneNumber());
    }

    public List<Person> findEntryByString(String keyWord) {
        List<Person> matchedPerson = new ArrayList<Person>();
        for (Person person : this.contactList) {
            if (person.getName().contains(keyWord)
            || person.getPhoneNumber().contains(keyWord))
                matchedPerson.add(person);
        }
        return matchedPerson;
    }

    // Returns the maximum ID in this contactList
//    public long findMaxID() {
//        long maxID = 1;
//        for (Person person : this.contactList) {
//            if(maxID <= person.getID()) {
//                maxID = person.getID();
//            }
//        }
//        return maxID;
//    }
}
