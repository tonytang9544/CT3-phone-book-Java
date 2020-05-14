package ct3PhoneBookTest.userInterface.commandLine;

import ct3PhoneBook.userInterface.commandLine.UserCommand;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UserCommandTest {

    @Test
    void ofCode() {
        assertEquals(UserCommand.ADD_ENTRY, UserCommand.ofCode("add"));
        assertEquals(UserCommand.DELETE_ENTRY, UserCommand.ofCode("del"));
        assertEquals(UserCommand.LIST_ALL_ENTRIES, UserCommand.ofCode("list"));
        assertThrows(IllegalArgumentException.class, () -> {
            UserCommand.ofCode("whatever");
        });
    }
}