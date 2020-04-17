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

    public ContactList findEntryByString(String keyWord) {
        ContactList matchedPerson = new ContactList();
        for (Person person : this.contactList) {
            if (person.toString().contains(keyWord))
                matchedPerson.addEntry(person);
        }
        return matchedPerson;
    }

    public int getNumberOfEntries() {
        return this.contactList.size();
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
