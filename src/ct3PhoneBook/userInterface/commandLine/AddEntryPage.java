package ct3PhoneBook.userInterface.commandLine;

import ct3PhoneBook.contactList.ContactList;
import ct3PhoneBook.contactObjects.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class AddEntryPage {

    public static void addEntryPage(ContactList contactList) {
        while (true) {
            CommandLineUI.clearConsole();
            printAddEntryPageTitle();
            String userInput = CommandLineUI.getUserInput();
            if (UserCommand.CANCEL_COMMAND
                    == CommandLineUI.getUserCommand(userInput)) {
                return;
            }
            else if (userInput.equals("Person")
                    || userInput.equals("person")) {
                Person newPerson = getPersonDetails();
                if (CommandLineUI.isUserSure()){
                    contactList.addEntry(newPerson);
                    return;
                }
                else {
                    continue;
                }
            }
            else if (userInput.equals("Friend")
                    || userInput.equals("friend")) {
                Friend newFriend = getFriendDetails();
                if (CommandLineUI.isUserSure()){
                    contactList.addEntry(newFriend);
                    return;
                }
                else {
                    continue;
                }
            }
            else if (userInput.equals("WorkFriend")
                    || userInput.equals("workfriend")
                    || userInput.equals("workFriend")) {
                WorkFriend newWorkFriend = getWorkFriendDetails();
                if (CommandLineUI.isUserSure()){
                    contactList.addEntry(newWorkFriend);
                    return;
                }
                else {
                    continue;
                }
            }
            else {
                CommandLineUI.printErrorInputMsg();
            }
        }

    }

    private static Person getPersonDetails() {
        System.out.println("Please enter the name of the person:");
        String name = CommandLineUI.getUserInput();
        System.out.println("Please enter the phone number of the person:");
        String phoneNumber = CommandLineUI.getUserInput();
        return new Person(name, phoneNumber);
    }

    private static Friend getFriendDetails() {
        Person person = getPersonDetails();
        GregorianCalendar birthday = getDateOfBirth();
        System.out.println("Please enter the short note: ");
        String shortNote = CommandLineUI.getUserInput();
        return Friend.convertPersonToFriend(person, birthday, shortNote);
    }

    private static GregorianCalendar getDateOfBirth() {
        System.out.println("Please enter the date of birth in the " +
                "format yyyy-mm-dd:");
        String userInput = CommandLineUI.getUserInput();
        return Friend.stringToBirthday(userInput);
    }

    private static WorkFriend getWorkFriendDetails() {
        Person person = getPersonDetails();
        System.out.println("Please enter the organization:");
        String organization = CommandLineUI.getUserInput();
        CompanyPosition position = getCompanyPosition();
        return WorkFriend.convertPersonToWorkFriend(person, organization, position);
    }

    private static CompanyPosition getCompanyPosition() {
        while (true) {
            System.out.println("Please enter the company position: ");
            String userInput = CommandLineUI.getUserInput();
            try {
                return CompanyPosition.valueOf(userInput);
            }
            catch (Exception e) {
                CommandLineUI.printErrorInputMsg();
                continue;
            }
        }
    }

    private static void printAddEntryPageTitle() {
        CommandLineUI.printTypeCancelToCancel();
        System.out.println("Enter the type of the new contact: ");
    }
}
