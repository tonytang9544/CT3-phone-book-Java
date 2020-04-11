package ct3PhoneBook.contactList;

import ct3PhoneBook.contactList.SortList.SortByName;
import ct3PhoneBook.contactList.SortList.SortByPhoneNumber;
import ct3PhoneBook.contactObjects.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ContactList {
    private List<Person> contactList;

    public ContactList() {
        contactList = new ArrayList<Person>();
    }

    public void addEntry(Person person) {
        contactList.add(person);
    }

    public void addEntry(Friend friend) {
        contactList.add(friend);
    }

    public void addEntry(WorkFriend workFriend) {
        contactList.add(workFriend);
    }

    public Object getEntryByIndex(int index) {
        return contactList.get(index);
    }

    public void sortByName() {
        Collections.sort(this.contactList, new SortByName());
    }

    public void sortByPhoneNumber() {
        Collections.sort(this.contactList, new SortByPhoneNumber());
    }
}
