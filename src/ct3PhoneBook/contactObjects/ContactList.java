package ct3PhoneBook.contactObjects;

import java.util.ArrayList;
import java.util.List;

public class ContactList {
    private List<Object> contactList;

    public ContactList() {
        contactList = new ArrayList<Object>();
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
}
