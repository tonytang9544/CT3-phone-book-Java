package ct3PhoneBook.userInterface.GUI.addEntryWindow;


import ct3PhoneBook.userInterface.GUI.mainWindow.DrawMainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddEntryWindow {
    public static void start(DrawMainWindow mainWindow) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrawAddEntryWindow addEntryWindow = new DrawAddEntryWindow(mainWindow);
                addEntryWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                addEntryWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainWindow.getAddEntryButton().setText("Add");
                        mainWindow.getAddEntryButton().setEnabled(true);
                        addEntryWindow.dispose();
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int result = JOptionPane.showConfirmDialog(addEntryWindow,
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
                addEntryWindow.setVisible(true);
            }
        });
    }
}
