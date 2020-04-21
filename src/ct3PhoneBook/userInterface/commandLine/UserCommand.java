package ct3PhoneBook.userInterface.commandLine;

import java.util.Hashtable;

public enum UserCommand {
    ADD_ENTRY ("add"),
    DELETE_ENTRY("del"),
    FIND_ENTRY("find"),
    SORT_CONTACTS("sort"),
    END_PROGRAM("end"),
    ILLEGAL_COMMAND("ill"),
    SORT_CONTACTS_BY_NAME("sortbn"),
    SORT_CONTACTS_BY_PHONENUMBER("sortbp"),
    LIST_ALL_ENTRIES("list"),
    CANCEL_COMMAND("cancel")
    ;

    private static final Hashtable<String, UserCommand> reflectionTable
            = new Hashtable<String, UserCommand>();
    static {
        for (UserCommand c : values()) {
            reflectionTable.put(c.commandCode, c);
        }
    }

    private String commandCode;

    private UserCommand(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getCommandCode() {
        return this.commandCode;
    }

    public static UserCommand ofCode(String code) {
        UserCommand result = reflectionTable.get(code);
        if (result == null) {
            throw new IllegalArgumentException("Invalid command code: " + code);
        }
        return result;
    }
}
