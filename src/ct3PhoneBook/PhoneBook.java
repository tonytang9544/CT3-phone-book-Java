package ct3PhoneBook;

import ct3PhoneBook.userInterface.commandLine.CommandLineMainPage;
import ct3PhoneBook.userInterface.commandLine.CommandLineUI;

public class PhoneBook {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("GUI function not yet supported.");
        }
        else if (args[0].equals("--headless")) {
            CommandLineMainPage.mainPage();
        }
        else {
            System.out.println("Invalid input. Start in headless mode by default");
            CommandLineMainPage.mainPage();
        }
    }
}
