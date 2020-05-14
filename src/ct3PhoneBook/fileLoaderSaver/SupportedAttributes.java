package ct3PhoneBook.fileLoaderSaver;

import ct3PhoneBook.userInterface.commandLine.UserCommand;

import java.util.Hashtable;

public enum SupportedAttributes {
    NAME("FN"),
    LAST_FIRST_NAME("N"),
    PHONENUMBER("TEL"),
    BIRTHDAY("BDAY"),
    SHORT_NOTES("NOTE"),
    ORGANIZATION("ORG"),
    COMPANY_POSITION("TITLE")
    ;

    private final String correspondingVcardValues;
    private static final Hashtable<String, SupportedAttributes> reflectionTable
            = new Hashtable<>();

    static {
        for (SupportedAttributes c : values()) {
            reflectionTable.put(c.correspondingVcardValues, c);
        }
    }

    public static SupportedAttributes ofVcardAttribute (String attribute) {
        SupportedAttributes result = reflectionTable.get(attribute);
        if (result == null) {
            throw new IllegalArgumentException("Invalid command code: " + attribute);
        }
        return result;
    }

    private SupportedAttributes(String attribute) {
        this.correspondingVcardValues = attribute;
    }

    public String getCorrespondingVcardValues() {
        return this.correspondingVcardValues;
    }

}
