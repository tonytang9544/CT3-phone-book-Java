package ct3PhoneBook;

import ct3PhoneBook.userInterface.GUI.mainWindow.MainWindow;
import ct3PhoneBook.userInterface.commandLine.CommandLineMainPage;

public class PhoneBook {
    public static void main(String[] args) {
        if (args.length == 0) {
            MainWindow.start();
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
