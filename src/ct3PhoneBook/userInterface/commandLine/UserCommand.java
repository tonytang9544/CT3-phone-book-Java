package ct3PhoneBook.userInterface.commandLine;

import java.util.Hashtable;

public enum UserCommand {
    ADD_ENTRY ("add"),
    DELETE_ENTRY("del"),
    FIND_ENTRY("find"),
    SORT_CONTACTS("sort"),
    QUIT_PROGRAM("quit"),
    ILLEGAL_COMMAND("illegal command"),
    SORT_CONTACTS_BY_NAME("sort by name"),
    SORT_CONTACTS_BY_PHONENUMBER("sort by phone number"),
    LIST_ALL_ENTRIES("list"),
    CANCEL_COMMAND("cancel"),
    IMPORT_FROM_FILE("import"),
    EXPORT_TO_FILE("export")
    ;

    private static final Hashtable<String, UserCommand> reflectionTable
            = new Hashtable<>();
    static {
        for (UserCommand c : values()) {
            reflectionTable.put(c.commandCode, c);
        }
    }

    private final String commandCode;

    private UserCommand(String commandCode) {
        this.commandCode = commandCode;
    }

    public static UserCommand ofCode(String code) {
        UserCommand result = reflectionTable.get(code);
        if (result == null) {
            throw new IllegalArgumentException("Invalid command code: " + code);
        }
        return result;
    }
}
