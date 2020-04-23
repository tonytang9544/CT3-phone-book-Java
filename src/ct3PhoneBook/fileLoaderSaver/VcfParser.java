package ct3PhoneBook.fileLoaderSaver;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.Person;

import java.util.ArrayList;
import java.util.List;


public class VcfParser {
    private String filePath;

    public VcfParser() {
    }

    public VcfParser(String path) {
        this.filePath = path;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }



    private List<String> getAttributesFromContact (String allData) {
        return null;
    }

    private static Person parsePerson(String personData) {
        return null;
    }
}
