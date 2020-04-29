package ct3PhoneBook.fileLoaderSaver;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.*;

import java.io.*;;
import java.util.*;


public class VcfParser {

    public static ContactList parseVcf(String filePath) throws IOException {
        ContactList parsedContacts = new ContactList();
        BufferedReader reader = prepareFileToRead(filePath);
        List<String> segmentedFile = segmentFile(reader);
        for (String onePerson : segmentedFile) {
            Hashtable<SupportedAttributes, String> formattedData
                    = getAllAttributes(onePerson);
            if (!formattedData.isEmpty()) {
                Person parsedPerson = parsePerson(formattedData);
                if (null != parsedPerson) {
                    parsedContacts.addEntry(parsedPerson);
                }
            }
        }
        return parsedContacts;
    }

    private static Person parsePerson(
            Hashtable<SupportedAttributes, String> personData) {
        if (!personData.containsKey(SupportedAttributes.NAME)
            || !personData.containsKey(SupportedAttributes.PHONENUMBER)) {
            return null;
        }
        if (personData.containsKey(SupportedAttributes.BIRTHDAY)
            && personData.containsKey(SupportedAttributes.SHORT_NOTES)) {
            return parseFriend(personData);
        }
        else if (personData.containsKey(SupportedAttributes.ORGANIZATION)
                && personData.containsKey(SupportedAttributes.COMPANY_POSITION)) {
            return parseWorkFriend(personData);
        }
        else {
            return new Person(personData.get(SupportedAttributes.NAME),
                    personData.get(SupportedAttributes.PHONENUMBER));
        }
    }

    private static Person parseWorkFriend (
            Hashtable<SupportedAttributes, String> personData) {
        try {
            CompanyPosition position
                    = CompanyPosition.valueOf(personData.get(
                            SupportedAttributes.COMPANY_POSITION));
            return new WorkFriend(personData.get(SupportedAttributes.NAME),
                    personData.get(SupportedAttributes.PHONENUMBER),
                    personData.get(SupportedAttributes.ORGANIZATION),
                    position);
        }
        catch (Exception e) {
            return new Person(personData.get(SupportedAttributes.NAME),
                    personData.get(SupportedAttributes.PHONENUMBER));
        }
    }

    private static Person parseFriend(
            Hashtable<SupportedAttributes, String> personData) {
        try {
            GregorianCalendar birthday = new GregorianCalendar();
            String birthdayToParse = personData.get(
                    SupportedAttributes.BIRTHDAY);
            int year = Integer.parseInt(birthdayToParse.substring(0, 4));
            int month = Integer.parseInt(birthdayToParse.substring(4, 6));
            int day = Integer.parseInt(birthdayToParse.substring(6, 8));
            birthday.set(year, month, day);
            return new Friend(personData.get(SupportedAttributes.NAME),
                    personData.get(SupportedAttributes.PHONENUMBER),
                    birthday,
                    personData.get(SupportedAttributes.SHORT_NOTES));
        }
        catch (Exception e) {
            return new Person(personData.get(SupportedAttributes.NAME),
                    personData.get(SupportedAttributes.PHONENUMBER));
        }
    }

    private static Hashtable<SupportedAttributes, String> getAllAttributes(
            String personData) {
        Scanner lineReader = new Scanner(personData);

        String oneLine = "";
        Hashtable<SupportedAttributes, String> newPerson = new Hashtable<>();
        while (lineReader.hasNextLine()) {
            oneLine = lineReader.nextLine();
            String[] parsedLine = getAttributeFromLine(oneLine);
            if (parsedLine != null) {
                SupportedAttributes currentAttribute
                        = SupportedAttributes.valueOf(parsedLine[0]);
                if (newPerson.containsKey(currentAttribute)) {
                    continue;
                }
                else {
                    newPerson.put(currentAttribute, parsedLine[1]);
                }
            }

        }
        return newPerson;
    }

    private static String[] getAttributeFromLine (String line) {

        String[] segmentedLine = line.split(":");
        if (1 >= segmentedLine.length) {
            return null;
        }

        String attributeType = segmentedLine[0].split(";")[0];
        String valueToParse = segmentedLine[1];

        try {
            SupportedAttributes currentAttribute
                    = SupportedAttributes.ofVcardAttribute(attributeType);

            switch (currentAttribute) {
                case NAME:
                    return new String[]{
                            SupportedAttributes.NAME.name(),
                            valueToParse};
                case LAST_FIRST_NAME:
                    return new String[]{
                            SupportedAttributes.NAME.name(),
                            formatNameFromN(valueToParse)};
                case PHONENUMBER:
                    return new String[]{
                            SupportedAttributes.PHONENUMBER.name(),
                            valueToParse};
                case BIRTHDAY:
                    return new String[]{
                            SupportedAttributes.BIRTHDAY.name(),
                            valueToParse};
                case SHORT_NOTES:
                    return new String[]{
                            SupportedAttributes.SHORT_NOTES.name(),
                            valueToParse};
                case ORGANIZATION:
                    return new String[]{
                            SupportedAttributes.ORGANIZATION.name(),
                            valueToParse};
                case COMPANY_POSITION:
                    return new String[]{
                            SupportedAttributes.COMPANY_POSITION.name(),
                            valueToParse};
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }


    private static String formatNameFromN(String valueToParse) {
        String[] names = valueToParse.split(";", -2);
        String formattedName = "";
        if (5 == names.length) {
            if (!names[3].equals("")) {
                formattedName += names[3];
                formattedName += " ";
            }
            if (!names[1].equals("")) {
                formattedName += names[1];
                formattedName += " ";
            }
            if (!names[2].equals("")) {
                formattedName += names[2];
                formattedName += " ";
            }
            if (!names[0].equals("")) {
                formattedName += names[0];
                formattedName += " ";
            }
            return formattedName;
        }
        else {
            for (String s : names) {
                formattedName += s;
                formattedName += " ";
            }
            return formattedName;
        }
    }

    private static List<String> segmentFile (BufferedReader reader)
            throws IOException {
        boolean hasBegun = false;
        String nextLine = reader.readLine();
        ArrayList<String> segmentedFile = new ArrayList<>();
        String currentContact = "";
        while (nextLine != null) {
            if (!hasBegun) {
                if (nextLine.equals("BEGIN:VCARD")) {
                    hasBegun = true;
                }
                nextLine = reader.readLine();
                continue;
            }
            else {
                if (nextLine.equals("END:VCARD")) {
                    hasBegun = false;
                    if (!currentContact.equals("")) {
                        segmentedFile.add(currentContact);
                        currentContact = "";
                    }
                }
                else {
                    currentContact += nextLine;
                    currentContact += "\n";
                }
                nextLine = reader.readLine();
                continue;
            }
        }
        reader.close();
        return segmentedFile;
    }

    private static BufferedReader prepareFileToRead(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.isFile()) {
            throw new IOException("Error! File: " + filePath + " is not a file.");
        }
        if (!file.exists()) {
            throw new IOException("Error! File: " + filePath + " does not exist.");
        }
        return new BufferedReader(new FileReader(file));
    }
}
