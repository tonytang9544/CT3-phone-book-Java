package ct3PhoneBook.userInterface.GUI.entryWindow;


import ct3PhoneBook.contactObjects.Person;
import ct3PhoneBook.userInterface.GUI.mainWindow.DrawMainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EntryWindow {
    public static void start(DrawMainWindow mainWindow, Person person) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrawEntryWindow entryWindow = new DrawEntryWindow(mainWindow, person);
                entryWindow.setFocusable(true);
                entryWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                entryWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainWindow.getAddEntryButton().setText("Add");
                        mainWindow.getAddEntryButton().setEnabled(true);
                        entryWindow.dispose();
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int result = JOptionPane.showConfirmDialog(entryWindow,
                                "Are you sure to leave? No changes will be recorded.",
                                "", JOptionPane.YES_NO_OPTION);
                        if (result == 0) {
                            windowClosed(e);
                        }
                        else {
                            return;
                        }
                    }
                });
                entryWindow.setVisible(true);
            }
        });
    }
}
