package ct3PhoneBook.contactObjects;

import java.util.Date;

public class Friend extends Person {
    private Date birthday;
    private String shortNotes;

    public Friend(String name,
                  String phoneNumber,
                  Date birthday,
                  String shortNotes) {
        super(name, phoneNumber);
        this.birthday = birthday;
        this.shortNotes = shortNotes;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getShortNotes() {
        return shortNotes;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setShortNotes(String shortNotes) {
        this.shortNotes = shortNotes;
    }


}
