package ct3PhoneBook.contactObjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Friend extends Person {
    private GregorianCalendar birthday;
    private String shortNotes;

    public Friend(String name,
                  String phoneNumber,
                  GregorianCalendar birthday,
                  String shortNotes) {
        super(name, phoneNumber);
        this.birthday = birthday;
        this.shortNotes = shortNotes;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public String getShortNotes() {
        return shortNotes;
    }

    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    public void setShortNotes(String shortNotes) {
        this.shortNotes = shortNotes;
    }

    public static Friend convertPersonToFriend(Person person,
                                               GregorianCalendar birthday,
                                               String shortNotes) {
        return new Friend(person.getName(),
                person.getPhoneNumber(),
                birthday,
                shortNotes);
    }

    @Override
    public String toString() {
        int year = this.getBirthday().get(GregorianCalendar.YEAR);
        int month = this.getBirthday().get(GregorianCalendar.MONTH);
        int day = this.getBirthday().get(GregorianCalendar.DAY_OF_MONTH);
        return this.getName() + ' '
                + this.getPhoneNumber() + ' '
                + year + '-' + month + '-' + day + ' '
                + this.getShortNotes();
    }
}
