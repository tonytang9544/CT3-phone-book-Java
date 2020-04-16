package ct3PhoneBook.contactObjects;

public class Person {
    private long ID;
    // Note that this ID is assigned in runtime,
    // does not correspond to the person with specific attributes.
    private String name;
    private String phoneNumber;
    private static long idAccumulator = 1;

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ID = idAccumulator;
        idAccumulator += 1;
    }

    public long getID() { return this.ID; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getName() {
        return this.name;
    }
}
