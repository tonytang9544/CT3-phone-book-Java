package ct3PhoneBook.userInterface.GUI.mainWindow;

import ct3PhoneBook.contactList.ContactList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow {

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrawMainWindow mainWindow = new DrawMainWindow();
                mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                mainWindow.setVisible(true);
                mainWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int result = JOptionPane.showConfirmDialog(mainWindow,
                                "Are you sure to leave?",
                                "", JOptionPane.YES_NO_OPTION);
                        if (result == 0) {
                            windowClosed(e);
                        }
                        else {
                            return;
                        }
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        JOptionPane.showMessageDialog(mainWindow,
                                "Thanks for using CT3-Phone-Book",
                                "Thanks",
                                JOptionPane.PLAIN_MESSAGE);
                        mainWindow.dispose();
                        System.exit(0);
                    }
                });
            }
        });
    }
}
