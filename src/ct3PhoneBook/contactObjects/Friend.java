package ct3PhoneBook.contactObjects;

import ct3PhoneBook.fileLoaderSaver.SupportedAttributes;

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
        return this.getName() + ' '
                + this.getPhoneNumber() + ' '
                + birthdayToDashedString(this.getBirthday()) + ' '
                + this.getShortNotes();
    }

    public static String birthdayToDashedString(GregorianCalendar birthday) {
        return birthday.get(GregorianCalendar.YEAR) + "-"
                + (birthday.get(GregorianCalendar.MONTH) + 1) + "-"
                + birthday.get(GregorianCalendar.DAY_OF_MONTH);
    }

    public static String birthdayToZeroFilledString(GregorianCalendar birthday) {
        int year = birthday.get(GregorianCalendar.YEAR);
        int month = (birthday.get(GregorianCalendar.MONTH) + 1);
        int day = birthday.get(GregorianCalendar.DAY_OF_MONTH);

        String monthString = month < 10 ? "0" + month : "" + month;
        String dayString = day < 10 ? "0" + day : "" + day;
        return year + monthString + dayString;
    }

    // Only support format yyyymmdd or yyyy-mm-dd
    public static GregorianCalendar stringToBirthday(String userInput) {
        if (!containOnlyNumbersOrDashes(userInput)) {
            return null;
        }
        if (userInput.length() != 8 && !userInput.contains("-")) {
            return null;
        }
        try {
            GregorianCalendar birthday = new GregorianCalendar();
            int year, month, day;
            if (userInput.contains("-")) {
                String[] userInputDecantenated = userInput.split("-");
                year = Integer.parseInt(userInputDecantenated[0]);
                month = Integer.parseInt(userInputDecantenated[1]) - 1;
                day = Integer.parseInt(userInputDecantenated[2]);
            }
            else {
                year = Integer.parseInt(userInput.substring(0, 4));
                month = Integer.parseInt(userInput.substring(4, 6)) - 1;
                day = Integer.parseInt(userInput.substring(6, 8));
            }
            if (month < 0 || month >= 12 || day <= 0 || day > 31 || year < 0) {
                return null;
            }
            birthday.set(GregorianCalendar.YEAR, year);
            birthday.set(GregorianCalendar.MONTH, month);
            birthday.set(GregorianCalendar.DAY_OF_MONTH, day);
            return birthday;
        }
        catch (Exception e) {
            return null;
        }

    }

    private static boolean containOnlyNumbersOrDashes(String userInput) {
        for (Character i : userInput.toCharArray()) {
            if (!Character.isDigit(i) && !i.equals('-')) {
                return false;
            }
        }
        return true;
    }
}
