package ct3PhoneBook.contactList;

import ct3PhoneBook.contactList.SortList.*;
import ct3PhoneBook.contactObjects.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactList {

    private final List<Person> contactList;

    /**
     * Constructor creates an empty list of People
     */
    public ContactList() {
        contactList = new ArrayList<Person>();
    }

    public void addEntry(Person personToAdd) {
        contactList.add(personToAdd);
    }

    /**
     * Delete the Person that matches the person input, using List.remove()
     * Return true if deleted, return false if not found or NullPointerException
     *
     * @param personToDelete the Person object that potentially matches a member
     *                       in the ContactList
     * @return  true if successfully deleted a Person, false if not found.
     */
    public boolean delEntry(Person personToDelete) {
        try {
            return contactList.remove(personToDelete);
        } catch (NullPointerException e) {
            return false;
        }
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

    /**
     * Find Person based on any description of that person
     * Return a ContactList of Person if found
     * Return an empty ContactList if not found
     * @param keyWord the String for query
     * @return ContactList with zero or more People inside.
     */
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
